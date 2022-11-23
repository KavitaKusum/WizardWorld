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
import com.example.wizardworld.R
import com.example.wizardworld.databinding.FragmentSpellDetailsBinding
import com.example.wizardworld.domain.model.Spell
import com.example.wizardworld.presentation.SpellDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SpellDetailsFragment : Fragment() {
    private val productId = "productId"
    private lateinit var binding: FragmentSpellDetailsBinding
    private val viewModel: SpellDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpellDetailsBinding.inflate(layoutInflater)
        arguments.let {
            viewModel.getSpellDetails(it?.getString(productId) ?: "")
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

    private fun setViews(data: Spell) {
        with(binding) {
            heading.text = getString(R.string.spell_details)
            name.text = getString(R.string.name, data.name)
            isverbal.text = getString(R.string.canbeverbal, data.canBeVerbal)
            creator.text = getString(R.string.creator, data.creator)
            effect.text = getString(R.string.effect, data.effect)
            incantation.text = getString(R.string.incantation, data.incantation)
            light.text = getString(R.string.light, data.light)
            type.text = getString(R.string.type, data.type)
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
}