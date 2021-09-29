package com.example.tilesmatch.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tilesmatch.R
import com.example.tilesmatch.data.viewmodel.MainViewModel
import com.example.tilesmatch.databinding.FragmentGameBinding
import com.example.tilesmatch.enums.MoveDirection
import com.example.tilesmatch.framework.BaseFragment
import com.example.tilesmatch.models.Resource
import com.example.tilesmatch.models.Status
import com.example.tilesmatch.models.Tile
import com.example.tilesmatch.utils.MoveHelperUtils
import com.example.tilesmatch.utils.extensions.glide
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class GameFragment : BaseFragment() {

    // Global
    private val TAG = GameFragment::class.java.simpleName
    private lateinit var binding: FragmentGameBinding
    private val args by navArgs<GameFragmentArgs>()
    private val viewModel by viewModels<MainViewModel>()
    private val glide by lazy { glide() }
    private val data = mutableListOf<Tile>()
    private val currentData = mutableListOf<Tile>()
    private var count by Delegates.observable(0) { _, _, new ->
        binding.txtMovesCounter.text = "Moves: $new"
    }
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

        binding.txtMovesCounter.text = "Moves: $count"

        setRecyclerview()

        setListeners()

        if (viewModel.tiles.value?.data.isNullOrEmpty()) viewModel.getGameTiles(glide, args.option)

        viewModel.tiles
            .observe(viewLifecycleOwner, { resource -> handleTilesResource(resource) })

        return binding.root
    }

    private fun setRecyclerview() {
        adapter = TilesAdapter(glide)
        adapter?.setListener(object : TilesAdapter.Listener {
            override fun onMove(position: Int, direction: MoveDirection) {
                val targetPosition = MoveHelperUtils.getValidTargetPosition(position, direction)
                if (targetPosition > -1 && currentData[targetPosition].bitmap == null) {
                    ++count
                    val temp = currentData[position].copy()
                    currentData[position] = currentData[targetPosition].copy()
                    currentData[targetPosition] = temp
                    adapter?.swapData(currentData)
                    validateState()
                }
            }
        })
        binding.recyclerTiles.adapter = adapter
    }

    private fun setListeners() {
        binding.imgBtnBack.setOnClickListener { this.onBackPressed() }
        binding.btnReset.setOnClickListener {
            count = 0
            currentData.clear()
            currentData.addAll(data)
            adapter?.swapData(currentData)
        }
    }

    private fun validateState() {
        if (isValid()) {
            Log.d(TAG, "TestLog: You Won!")
        }
    }

    private fun isValid(): Boolean {
        currentData.forEachIndexed { i, v -> if (i != v._id) return false }
        return true
    }

    private fun handleTilesResource(resource: Resource<List<Tile>>) {
        when (resource.status) {
            Status.LOADING -> Log.d(TAG, "TestLog: Loading")
            Status.ERROR -> this.onBackPressed()
            Status.SUCCESS -> {
                val list = resource.data
                if (list.isNullOrEmpty()) {
                    this.onBackPressed()
                    return
                }
                data.clear()
                data.addAll(list)
                currentData.clear()
                currentData.addAll(list)
                adapter?.swapData(currentData)
            }
        }
    }

    override fun onBackPressed() {
        viewModel.clearGameTiles()
        super.onBackPressed()
    }
}