package com.example.tilesmatch.ui.game

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tilesmatch.R
import com.example.tilesmatch.databinding.LayoutTilesItemBinding
import com.example.tilesmatch.enums.MoveDirection
import com.example.tilesmatch.models.Tile
import kotlin.math.abs

class TilesAdapter : ListAdapter<Tile, TilesAdapter.ViewHolder>(TileDC()) {

    // Global
    private val TAG = TilesAdapter::class.java.simpleName
    private var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_tiles_item,
            parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(position)

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    fun swapData(data: List<Tile>) = submitList(data.toMutableList())

    inner class ViewHolder(
        private val binding: LayoutTilesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(position: Int) = with(binding) {
            val tile = getItem(position)
            val bitmap = tile.bitmap
            val isBlank = bitmap == null
            @ColorRes val colorRes =
                if (isBlank) android.R.color.holo_blue_light else android.R.color.holo_green_light
            txtId.apply {
                text = "${tile._id}"
                setBackgroundColor(ResourcesCompat.getColor(resources, colorRes, null))
            }
            var x = 0F
            var y = 0F
            if (isBlank) root.setOnTouchListener { _, mE ->
                when (mE.action) {
                    MotionEvent.ACTION_DOWN -> {
                        x = mE.rawX
                        y = mE.rawY
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        val dx = mE.rawX - x
                        val dy = mE.rawY - y
                        val isXNegative = dx < 0
                        val isYNegative = dy < 0
                        val isHorizontal = abs(dx) > abs(dy)
                        val direction = if (isHorizontal) {
                            if (isXNegative) MoveDirection.LEFT else MoveDirection.RIGHT
                        } else {
                            if (isYNegative) MoveDirection.UP else MoveDirection.DOWN
                        }
                        listener?.onMove(position, direction)
                    }
                }
                true
            }
        }
    }

    interface Listener {
        fun onMove(position: Int, direction: MoveDirection)
    }

    private class TileDC : DiffUtil.ItemCallback<Tile>() {
        override fun areItemsTheSame(
            oldItem: Tile,
            newItem: Tile
        ): Boolean = oldItem._id == newItem._id

        override fun areContentsTheSame(
            oldItem: Tile,
            newItem: Tile
        ): Boolean = oldItem == newItem
    }
}