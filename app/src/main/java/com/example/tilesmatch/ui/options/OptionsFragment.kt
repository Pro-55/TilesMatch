package com.example.tilesmatch.ui.options

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tilesmatch.R
import com.example.tilesmatch.data.viewmodel.MainViewModel
import com.example.tilesmatch.databinding.FragmentOptionsBinding
import com.example.tilesmatch.framework.BaseFragment
import com.example.tilesmatch.models.Option
import com.example.tilesmatch.models.Resource
import com.example.tilesmatch.models.Status
import com.example.tilesmatch.utils.extensions.glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionsFragment : BaseFragment() {

    // Global
    private val TAG = OptionsFragment::class.java.simpleName
    private lateinit var binding: FragmentOptionsBinding
    private val viewModel by viewModels<MainViewModel>()
    private var adapter: OptionsAdapter? = null
    private val data = mutableListOf<Option>()

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

        setListeners()

        if (data.isNullOrEmpty()) viewModel.getOptions()

        viewModel.options
            .observe(viewLifecycleOwner, { resource -> handleOptionsResource(resource) })

        return binding.root
    }

    private fun setupRecyclerview() {
        adapter = OptionsAdapter(glide())
        adapter?.setListener(object : OptionsAdapter.Listener {
            override fun onItemClick(option: Option) {
                val action = OptionsFragmentDirections.navigateOptionsToGame(option)
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

    private fun handleOptionsResource(resource: Resource<List<Option>>) {
        when (resource.status) {
            Status.LOADING -> Log.d(TAG, "TestLog: Loading")
            Status.ERROR -> Log.d(TAG, "TestLog: e:${resource.message}")
            Status.SUCCESS -> {
                val list = resource.data
                if (list.isNullOrEmpty()) {
                    Log.d(TAG, "TestLog: Error")
                    return
                }
                adapter?.swapData(list)
            }
        }
    }
}