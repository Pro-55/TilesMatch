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

class GameFragment : BaseFragment() {

    // Global
    private val TAG = GameFragment::class.java.simpleName
    private lateinit var binding: FragmentGameBinding
    private var adapter: TilesAdapter? = null
    private var count = 0

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

        updateCount()

        setRecyclerview()

        updateData()

        setListeners()

        return binding.root
    }

    private fun updateCount() {
        binding.txtMovesCounter.text = "Moves: $count"
    }

    private fun setRecyclerview() {
        adapter = TilesAdapter()
        adapter?.setListener(object : TilesAdapter.Listener {
            override fun onMove(position: Int, direction: MoveDirection) {
                Log.d(TAG, "TestLog: $position:$direction")
            }
        })
        binding.recyclerTiles.adapter = adapter
    }

    private fun setListeners() {
        binding.imgBtnBack.setOnClickListener { onBackPressed() }
        binding.btnReset.setOnClickListener { }
    }

    private fun updateData() {
        val data = mutableListOf<Tile>()
        for (i in 0..14) {
            data.add(Tile(_id = i, bitmap = "$i"))
        }
        data.add(Tile(_id = 15, bitmap = null))
        adapter?.swapData(data)
    }
}