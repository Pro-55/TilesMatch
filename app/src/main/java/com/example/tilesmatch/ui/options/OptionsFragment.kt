package com.example.tilesmatch.ui.options

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tilesmatch.R
import com.example.tilesmatch.databinding.FragmentOptionsBinding
import com.example.tilesmatch.framework.BaseFragment
import com.example.tilesmatch.models.Option

class OptionsFragment : BaseFragment() {

    // Global
    private val TAG = OptionsFragment::class.java.simpleName
    private lateinit var binding: FragmentOptionsBinding
    private var adapter: OptionsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_options,
            container, false
        )

        setupRecyclerview()

        updateData()

        setListeners()

        return binding.root
    }

    private fun setupRecyclerview() {
        adapter = OptionsAdapter()
        adapter?.setListener(object : OptionsAdapter.Listener {
            override fun onItemClick(url: String?) {
                val action = OptionsFragmentDirections.navigateOptionsToGame()
                findNavController().navigate(action)
            }
        })
        binding.recyclerOptions.adapter = adapter
    }

    private fun setListeners() {
        binding.recyclerOptions.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                binding.efabSelect.apply { if (newState == RecyclerView.SCROLL_STATE_IDLE) extend() else shrink() }
            }
        })
        binding.efabSelect.setOnClickListener { }
    }

    private fun updateData() {
        val data = mutableListOf<Option>()
        for (i in 0 until 999) {
            data.add(Option(_id = "$i", url = "$i"))
        }
        adapter?.swapData(data)
    }
}