package com.example.tilesmatch.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.tilesmatch.R
import com.example.tilesmatch.databinding.FragmentGameBinding
import com.example.tilesmatch.enums.MoveDirection
import com.example.tilesmatch.framework.BaseFragment
import com.example.tilesmatch.models.Tile
import com.example.tilesmatch.utils.MoveHelperUtils
import kotlin.properties.Delegates

class GameFragment : BaseFragment() {

    // Global
    private val TAG = GameFragment::class.java.simpleName
    private lateinit var binding: FragmentGameBinding
    private var adapter: TilesAdapter? = null
    private val data = mutableListOf<Tile>()
    private val currentData = mutableListOf<Tile>()
    private var count by Delegates.observable(0) { _, _, new ->
        binding.txtMovesCounter.text = "Moves: $new"
    }

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

        updateData()

        setListeners()

        return binding.root
    }

    private fun setRecyclerview() {
        adapter = TilesAdapter()
        adapter?.setListener(object : TilesAdapter.Listener {
            override fun onMove(position: Int, direction: MoveDirection) {
                val targetPosition = MoveHelperUtils.getValidTargetPosition(position, direction)
                if (targetPosition > -1 && currentData[targetPosition].bitmap == null) {
                    ++count
                    val temp = currentData[position]
                    currentData[position] = currentData[targetPosition]
                    currentData[targetPosition] = temp
                    adapter?.swapData(currentData)
                    validateState()
                }
            }
        })
        binding.recyclerTiles.adapter = adapter
    }

    private fun setListeners() {
        binding.imgBtnBack.setOnClickListener { onBackPressed() }
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

    private fun updateData() {
        for (i in 0..14) {
            data.add(Tile(_id = i, bitmap = "$i"))
        }
        data.add(Tile(_id = 15, bitmap = null))
        currentData.addAll(data)
        adapter?.swapData(currentData)
    }
}