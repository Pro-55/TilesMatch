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
import com.example.tilesmatch.utils.TouchHelperUtils

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
        holder.bind(getItem(position))

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    fun swapData(data: List<Tile>) = submitList(data.toMutableList())

    inner class ViewHolder(
        private val binding: LayoutTilesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(tile: Tile) = with(binding) {
            val bitmap = tile.bitmap
            val isBlank = bitmap == null
            @ColorRes val colorRes =
                if (isBlank) android.R.color.holo_blue_light else android.R.color.holo_green_light
            imgTile.setBackgroundColor(ResourcesCompat.getColor(root.resources, colorRes, null))
            var x = 0F
            var y = 0F
            if (!isBlank) {
                root.setOnTouchListener { _, mE ->
                    when (mE.action) {
                        MotionEvent.ACTION_DOWN -> {
                            x = mE.rawX
                            y = mE.rawY
                        }
                        MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> listener?.onMove(
                            absoluteAdapterPosition,
                            TouchHelperUtils.getMoveDirection(mE, x, y)
                        )
                    }
                    true
                }
            } else {
                root.setOnTouchListener(null)
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
        ): Boolean = oldItem._id == newItem._id
    }
}