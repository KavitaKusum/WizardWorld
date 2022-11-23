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
import com.example.wizardworld.databinding.FragmentHouseDetailsBinding
import com.example.wizardworld.domain.model.House
import com.example.wizardworld.presentation.HouseDetailsViewModel
import com.example.wizardworld.ui.home.adapter.DetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HouseDetailsFragment : Fragment() {
    private val productId = "productId"
    private lateinit var binding: FragmentHouseDetailsBinding
    private val viewModel: HouseDetailsViewModel by viewModels()
    private val headsAdapter by lazy { DetailsAdapter() }
    private val traitsAdapter by lazy { DetailsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHouseDetailsBinding.inflate(layoutInflater)
        setHeadsView()
        setTraitsView()
        arguments.let {
            viewModel.getHouseDetails(it?.getString(productId) ?: "")
        }
        lifecycleScope.launch {
            viewModel.viewState.collect { viewState ->
                if (viewState.isLoading) showLoading()
                viewState.error?.let {
                    hideLoading()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_text, viewState.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                viewState.data?.let {
                    hideLoading()
                    setViews(it)
                }
            }
        }
        return binding.root
    }

    private fun setViews(data: House) {
        with(binding) {
            heading.text = getString(R.string.house_details)
            houseName.text = getString(R.string.name, data.name)
            houseColor.text = getString(R.string.color, data.houseColours)
            houseFounder.text = getString(R.string.founder, data.founder)
            houseAnimal.text = getString(R.string.animal, data.animal)
            houseElement.text = getString(R.string.element, data.element)
            houseGhost.text = getString(R.string.ghost, data.ghost)
            houseCommonroom.text = getString(R.string.commonroom, data.commonRoom)
            if (data.heads.isEmpty())
                headsRv.isVisible = false
            else {
                headsAdapter.differ.submitList(data.heads)
                headsLabel.text = getString(R.string.heads)
            }
            if (data.traits.isEmpty())
                traitsRv.isVisible = false
            else {
                traitsAdapter.differ.submitList(data.traits)
                traitsLabel.text = getString(R.string.traits)
            }
        }
    }

    private fun hideLoading() {
        with(binding) {
            progressBar.isVisible = false
            detail.isVisible = true
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            detail.isVisible = false
        }
    }

    private fun setTraitsView() {
        with(binding.traitsRv) {
            layoutManager = LinearLayoutManager(context)
            adapter = traitsAdapter
        }
    }

    private fun setHeadsView() {
        with(binding.headsRv) {
            layoutManager = LinearLayoutManager(context)
            adapter = headsAdapter
        }
    }
}