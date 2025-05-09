package com.papslabs.tilesmatch.ui.options

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.papslabs.tilesmatch.R
import com.papslabs.tilesmatch.data.viewmodel.MainViewModel
import com.papslabs.tilesmatch.databinding.FragmentOptionsBinding
import com.papslabs.tilesmatch.framework.BaseFragment
import com.papslabs.tilesmatch.models.Option
import com.papslabs.tilesmatch.models.Resource
import com.papslabs.tilesmatch.models.Status
import com.papslabs.tilesmatch.utils.Constants
import com.papslabs.tilesmatch.utils.TapTargets
import com.papslabs.tilesmatch.utils.extensions.glide
import com.papslabs.tilesmatch.utils.extensions.showConfirmationDialog
import com.papslabs.tilesmatch.utils.extensions.showShortSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OptionsFragment : BaseFragment() {

    // Global
    private val TAG = OptionsFragment::class.java.simpleName
    private lateinit var binding: FragmentOptionsBinding
    @Inject lateinit var sp: SharedPreferences
    private val viewModel by viewModels<MainViewModel>()
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

        setListeners()

        if (viewModel.options.value?.data.isNullOrEmpty()) viewModel.getOptions()

        viewModel.options
            .observe(viewLifecycleOwner) { resource -> handleOptionsResource(resource) }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (sp.getBoolean(Constants.SHOULD_EXPLAIN_SELECT, true)) {
            sp.edit { putBoolean(Constants.SHOULD_EXPLAIN_SELECT, false) }
            TapTargets.show(
                requireActivity(),
                TapTargets.Input(binding.fabSettings, TapTargets.Type.SELECT)
            )
        }
    }

    /**
     * set ups recycler view with adapter
     */
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

    /**
     * sets listeners to views
     */
    private fun setListeners() {
        binding.fabSettings.setOnClickListener { openSettingsSheet() }
    }

    /**
     * handle options response received from view model
     */
    private fun handleOptionsResource(resource: Resource<List<Option>>) {
        if (resource.status != Status.LOADING) {
            val list = resource.data
            if (list.isNullOrEmpty()) {
                showShortSnackBar(resource.message ?: Constants.MSG_SOMETHING_WENT_WRONG)
                requireActivity().showConfirmationDialog(
                    icon = R.drawable.ic_warning,
                    title = "Oops!",
                    message = "Something went wrong while fetching the data!\nDo you want to retry?",
                    positiveButtonClick = { viewModel.getOptions() },
                    negativeButtonClick = { this.onBackPressed() }
                )
                return
            }
            adapter?.swapData(list)
        }
    }

    private fun openSettingsSheet() {
        showShortSnackBar("Not there yet!")
    }
}