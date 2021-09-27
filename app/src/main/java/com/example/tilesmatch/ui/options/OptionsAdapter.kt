package com.example.tilesmatch.ui.options

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tilesmatch.R
import com.example.tilesmatch.databinding.LayoutOptionsItemBinding
import com.example.tilesmatch.models.Option

class OptionsAdapter : ListAdapter<Option, OptionsAdapter.ViewHolder>(OptionDC()) {

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

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    fun swapData(data: List<Option>) = submitList(data.toMutableList())

    inner class ViewHolder(
        private val binding: LayoutOptionsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(option: Option) = with(binding) {
            @ColorRes val colorRes =
                if (option.url.isNullOrEmpty()) android.R.color.holo_blue_light else android.R.color.holo_green_light
            txtId.apply {
                text = option._id
                setBackgroundColor(ResourcesCompat.getColor(resources, colorRes, null))
            }
            root.setOnClickListener { listener?.onItemClick(option.url) }
        }
    }

    interface Listener {
        fun onItemClick(url: String?)
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