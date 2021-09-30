package com.example.tilesmatch.ui.options

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tilesmatch.R
import com.example.tilesmatch.databinding.LayoutOptionsItemBinding
import com.example.tilesmatch.models.Option

class OptionsAdapter(
    private val glide: RequestManager
) : ListAdapter<Option, OptionsAdapter.ViewHolder>(OptionDC()) {

    // Global
    private val TAG = OptionsAdapter::class.java.simpleName
    private var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_options_item,
            parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    /**
     * sets adapter listner
     */
    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    /**
     * swaps adapter data with current data
     */
    fun swapData(data: List<Option>) = submitList(data.toMutableList())

    inner class ViewHolder(
        private val binding: LayoutOptionsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(option: Option) = with(binding) {
            val url = when (option._id) {
                0 -> R.drawable.slytherin
                1 -> R.drawable.gryffindor
                2 -> R.drawable.hufflepuff
                3 -> R.drawable.ravenclaw
                else -> option.url
            }
            glide.load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgGame)

            root.setOnClickListener { listener?.onItemClick(option) }
        }
    }

    interface Listener {
        fun onItemClick(option: Option)
    }

    private class OptionDC : DiffUtil.ItemCallback<Option>() {
        override fun areItemsTheSame(
            oldItem: Option,
            newItem: Option
        ): Boolean = oldItem._id == newItem._id

        override fun areContentsTheSame(
            oldItem: Option,
            newItem: Option
        ): Boolean = oldItem == newItem
    }
}