package com.example.wizardworld.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wizardworld.R
import com.example.wizardworld.databinding.FragmentWizardDetailsBinding
import com.example.wizardworld.domain.model.Wizard
import com.example.wizardworld.presentation.WizardDetailsViewModel
import com.example.wizardworld.ui.home.adapter.DetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WizardDetailsFragment : Fragment(){
    private lateinit var binding: FragmentWizardDetailsBinding
    private val viewModel: WizardDetailsViewModel by viewModels()
    private val detailsAdapter by lazy{ DetailsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWizardDetailsBinding.inflate(layoutInflater)
        binding.specializationRv.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = detailsAdapter
        }
        arguments.let {
            viewModel.getWizardDetails(it?.getString("productId")?:"" )
        }
        lifecycleScope.launch {
            viewModel.viewState.collect { viewState ->
                if(viewState.isLoading) showLoading()
                viewState.error?.let {
                    hideLoading()
                    Toast.makeText(requireContext(), getString(R.string.error_text, viewState.error), Toast.LENGTH_SHORT).show()
                }
                viewState.data?.let {
                    hideLoading()
                    setViews(it)
                }
            }
        }
        return binding.root
    }

    private fun setViews(data: Wizard) {
        with(binding){
            heading.text=getString(R.string.wizards_details)
            wizardName.text= getString(R.string.name, data.name)
            if(data.elixirs.isEmpty())
                specializationRv.isVisible=false
            else{
                detailsAdapter.differ.submitList(viewModel.getElixirList(data.elixirs))
                specializationLable.text=getString(R.string.specialization)
            } 
        }
    }

    private fun showLoading() {
        with(binding){
            progressBar.isVisible = true
            detail.isVisible=false
        }
    }

    private fun hideLoading() {
        with(binding){
            progressBar.isVisible = false
            detail.isVisible=true
        }
    }
}