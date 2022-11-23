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
import com.example.wizardworld.databinding.FragmentElixirDetailsBinding
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.presentation.ElixirDetailsViewModel
import com.example.wizardworld.ui.home.adapter.DetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ElixirDetailsFragment : Fragment() {
    private val productId = "productId"
    private lateinit var binding: FragmentElixirDetailsBinding
    private val viewModel: ElixirDetailsViewModel by viewModels()
    private val ingredientsAdapter by lazy { DetailsAdapter() }
    private val inventorsAdapter by lazy { DetailsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentElixirDetailsBinding.inflate(layoutInflater)
        setIngredientsView()
        setInventorsView()
        arguments.let {
            viewModel.getElixirDetails(it?.getString(productId) ?: "")
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

    private fun setViews(data: Elixir) {
        with(binding) {
            heading.text = getString(R.string.elixir_details)
            elixirName.text = getString(R.string.name, data.name)
            elixirEffect.text = getString(R.string.effect, data.effect)
            elixirSideEffect.text = getString(R.string.side_effect, data.sideEffects)
            elixirCharacteristics.text = getString(R.string.characteristics, data.characteristics)
            elixirDifficulty.text = getString(R.string.difficulty, data.difficulty)
            elixirManufacturer.text = getString(R.string.manufacturer, data.manufacturer)
            elixirTime.text = getString(R.string.time, data.time)
            if (data.ingredients.isEmpty())
                ingredientsRv.isVisible = false
            else {
                ingredientsAdapter.differ.submitList(data.ingredients)
                ingredientsLabel.text = getString(R.string.inventors)
            }
            if (data.inventors.isEmpty())
                inventorsRv.isVisible = false
            else {
                ingredientsAdapter.differ.submitList(data.inventors)
                inventorsLabel.text = getString(R.string.ingredients)
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            detail.isVisible = false
        }
    }

    private fun hideLoading() {
        with(binding) {
            progressBar.isVisible = false
            detail.isVisible = true
        }
    }

    private fun setIngredientsView() {
        with(binding.ingredientsRv) {
            layoutManager = LinearLayoutManager(context)
            adapter = ingredientsAdapter
        }
    }

    private fun setInventorsView() {
        with(binding.inventorsRv) {
            layoutManager = LinearLayoutManager(context)
            adapter = inventorsAdapter
        }
    }
}