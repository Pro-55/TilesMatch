package com.papslabs.tilesmatch.ui.game

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.papslabs.tilesmatch.R
import com.papslabs.tilesmatch.data.viewmodel.MainViewModel
import com.papslabs.tilesmatch.databinding.FragmentGameBinding
import com.papslabs.tilesmatch.enums.MoveDirection
import com.papslabs.tilesmatch.framework.BaseFragment
import com.papslabs.tilesmatch.models.Move
import com.papslabs.tilesmatch.models.Option
import com.papslabs.tilesmatch.models.Resource
import com.papslabs.tilesmatch.models.Status
import com.papslabs.tilesmatch.models.Tile
import com.papslabs.tilesmatch.utils.Constants
import com.papslabs.tilesmatch.utils.TapTargets
import com.papslabs.tilesmatch.utils.extensions.getColorFromAttr
import com.papslabs.tilesmatch.utils.extensions.glide
import com.papslabs.tilesmatch.utils.extensions.showConfirmationDialog
import com.papslabs.tilesmatch.utils.extensions.showShortSnackBar
import com.papslabs.tilesmatch.utils.helpers.MoveHelperUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates
import com.google.android.material.R as mR

@AndroidEntryPoint
class GameFragment : BaseFragment() {

    // Global
    private val TAG = GameFragment::class.java.simpleName
    private lateinit var binding: FragmentGameBinding
    @Inject lateinit var sp: SharedPreferences
    private val args by navArgs<GameFragmentArgs>()
    private val viewModel by viewModels<MainViewModel>()
    private val glide by lazy { glide() }
    private var option: Option? = null
    private val data = mutableListOf<Tile>()
    private val currentData = mutableListOf<Tile>()
    private val moves = mutableListOf<Move>()
    private var count by Delegates.observable(0) { _, _, new ->
        binding.txtMovesCounter.text = resources.getString(R.string.label_count, new)
    }
    private var canGoBack = true
    private var adapter: TilesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container, false
        )

        option = args.option

        binding.txtGameTitle.text = option?.title

        binding.txtMovesCounter.text = resources.getString(R.string.label_count, count)

        setRecyclerview()

        setListeners()

        if (viewModel.tiles.value?.data.isNullOrEmpty()) viewModel.getGameTiles(glide, option)

        viewModel.tiles
            .observe(viewLifecycleOwner) { resource -> handleTilesResource(resource) }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val shouldExplainAssist = sp.getBoolean(Constants.SHOULD_EXPLAIN_ASSIST, true)
        val shouldExplainUndo = sp.getBoolean(Constants.SHOULD_EXPLAIN_UNDO, true)
        val shouldExplainReset = sp.getBoolean(Constants.SHOULD_EXPLAIN_RESET, true)

        if (shouldExplainAssist || shouldExplainUndo || shouldExplainReset) {
            val list = mutableListOf<TapTargets.Input>()
            if (shouldExplainAssist) {
                sp.edit { putBoolean(Constants.SHOULD_EXPLAIN_ASSIST, false) }
                list.add(TapTargets.Input(binding.switchEnableIds, TapTargets.Type.ASSIST))
            }
            if (shouldExplainUndo) {
                sp.edit { putBoolean(Constants.SHOULD_EXPLAIN_UNDO, false) }
                list.add(TapTargets.Input(binding.imgBtnUndo, TapTargets.Type.UNDO))
            }
            if (shouldExplainReset) {
                sp.edit { putBoolean(Constants.SHOULD_EXPLAIN_RESET, false) }
                list.add(TapTargets.Input(binding.imgBtnReset, TapTargets.Type.RESET))
            }
            TapTargets.show(requireActivity(), list)
        }
    }

    /**
     * set ups recycler view with adapter
     */
    private fun setRecyclerview() {
        adapter = TilesAdapter(glide)
        adapter?.setListener(object : TilesAdapter.Listener {
            override fun onMove(position: Int, direction: MoveDirection) {
                val targetPosition = MoveHelperUtils.getValidTargetPosition(position, direction)
                if (targetPosition > -1 && currentData[targetPosition].bitmap == null) {
                    moves.add(Move(position = position, targetPosition = targetPosition))
                    handleUndoButtonState()
                    ++count
                    swap(position, targetPosition)
                    validateState()
                }
            }
        })
        binding.recyclerTiles.adapter = adapter
    }

    /**
     * sets listeners to views
     */
    private fun setListeners() {
        binding.imgBtnBack.setOnClickListener { this.onBackPressed() }
        binding.switchEnableIds.setOnCheckedChangeListener { _, isChecked ->
            val list = currentData.map { it.copy(shouldShowId = isChecked) }
            updateCurrentData(list)
        }
        binding.imgBtnReset.setOnClickListener { resetGameTiles() }
        binding.imgBtnUndo.setOnClickListener { undoMove() }
    }

    /**
     * validate current state of board to determine if game has ended or not
     * if state is valid show confirmation dialog
     */
    private fun validateState() {
        if (isValid()) {
            requireActivity().showConfirmationDialog(
                icon = R.drawable.ic_celebration,
                title = "Congratulations!",
                message = "You Solved ${option?.title ?: "The Puzzle"} in $count Moves!\nWanna go again?",
                positiveButtonClick = { resetGameTiles() },
                negativeButtonClick = { this.onBackPressed() }
            )
        }
    }

    /**
     * @return flag denoting if current board config is valid or not
     */
    private fun isValid(): Boolean {
        currentData.forEachIndexed { i, v -> if (i != v._id) return false }
        return true
    }

    /**
     * handle tiles response received from view model
     */
    private fun handleTilesResource(resource: Resource<List<Tile>>) {
        if (resource.status != Status.LOADING) {
            val list = resource.data
            if (list.isNullOrEmpty()) {
                showShortSnackBar(resource.message ?: Constants.MSG_SOMETHING_WENT_WRONG)
                this.onBackPressed()
                return
            }
            moves.clear()
            handleUndoButtonState()
            data.clear()
            data.addAll(list)
            updateCurrentData(list)
        }
    }

    /**
     * resets game tiles to original configuration
     * and resets the move count
     */
    private fun resetGameTiles() {
        moves.clear()
        handleUndoButtonState()
        count = 0
        updateCurrentData(data)
        showShortSnackBar("Resetting...")
    }

    /**
     * undo last move
     */
    private fun undoMove() {
        if (moves.isNotEmpty()) {
            val lastMove = moves.removeAt(moves.size - 1)
            handleUndoButtonState()
            --count
            swap(lastMove.targetPosition, lastMove.position)
        }
    }

    /**
     * swap items on passed positions
     *
     * @param position current position of the item
     * @param targetPosition target position of the item
     */
    fun swap(position: Int, targetPosition: Int) {
        val temp = currentData[position].copy()
        currentData[position] = currentData[targetPosition].copy()
        currentData[targetPosition] = temp
        adapter?.swapData(currentData)
    }

    /**
     * updates current list data
     */
    private fun updateCurrentData(list: List<Tile>) {
        val mappedList = list.map { it.copy(shouldShowId = binding.switchEnableIds.isChecked) }
        currentData.clear()
        currentData.addAll(mappedList)
        adapter?.swapData(currentData)
    }

    /**
     * handle color state of undo button
     */
    private fun handleUndoButtonState() {
        val isMovesEmpty = moves.isEmpty()
        if (canGoBack != isMovesEmpty) canGoBack = isMovesEmpty
        @AttrRes val colorResId = if (isMovesEmpty) mR.attr.colorOutline else mR.attr.colorOnSurface
        binding.imgBtnUndo.setColorFilter(requireContext().getColorFromAttr(colorResId))
    }

    /**
     * show confirmation dialog before going back to the previous screen
     */
    private fun confirmBack() {
        requireActivity().showConfirmationDialog(
            icon = R.drawable.ic_warning,
            title = "Warning!",
            message = "All Your Progress Will Be Lost!\nSure You Want To Go Back?",
            positiveButtonClick = {
                canGoBack = true
                this.onBackPressed()
            }
        )
    }

    override fun onBackPressed() {
        if (canGoBack) {
            viewModel.clearGameTiles()
            super.onBackPressed()
        } else confirmBack()
    }
}