package com.example.wizardworld.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wizardworld.CHOICE
import com.example.wizardworld.POSITION_ID
import com.example.wizardworld.PRODUCT_ID
import com.example.wizardworld.R
import com.example.wizardworld.databinding.FragmentWizardsBinding
import com.example.wizardworld.presentation.ProductListViewModel
import com.example.wizardworld.presentation.ViewState
import com.example.wizardworld.ui.home.HomeActivity
import com.example.wizardworld.ui.home.adapter.ProductListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentWizardsBinding
    private val viewModel: ProductListViewModel by viewModels()
    private val productListAdapter by lazy { ProductListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as HomeActivity).binding.imgBack.isVisible = true
        var choice: Int
        arguments.let {
            choice = it?.getInt(POSITION_ID) ?: 0
        }
        binding = FragmentWizardsBinding.inflate(layoutInflater)
        with(binding.wizardsRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            productListAdapter.onClickListener = object :
                ProductListAdapter.OnClickListener {
                override fun onClick(productId: String) {
                    val bundle = bundleOf(PRODUCT_ID to productId)
                    when (choice) {
                        CHOICE.ONE.value -> findNavController().navigate(
                            R.id.action_to_WizardDetailsFragment,
                            bundle
                        )
                        CHOICE.TWO.value -> findNavController().navigate(
                            R.id.action_to_HouseDetailsFragment,
                            bundle
                        )
                        CHOICE.THREE.value -> findNavController().navigate(
                            R.id.action_to_ElixirDetailsFragment,
                            bundle
                        )
                        CHOICE.FOUR.value -> findNavController().navigate(
                            R.id.action_to_SpellDetailsFragment,
                            bundle
                        )
                    }
                }
            }
            adapter = productListAdapter
        }
        viewModel.getProductList(choice, requireContext())
        lifecycleScope.launch {
            viewModel.viewState.collect { viewState ->
                when (viewState) {
                    is ViewState.Error -> {
                        hideLoading()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_text, viewState.msg),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ViewState.Loading -> showLoading()
                    is ViewState.Success -> {
                        hideLoading()
                        setViews(choice, viewState.result)
                    }
                }
            }
        }
        return binding.root
    }

    private fun setViews(choice: Int, data: List<Triple<String, String, String>>) {
        hideLoading()
        productListAdapter.differ.submitList(data)
        binding.heading.text = viewModel.getHeadingText(choice, requireContext())
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            heading.isVisible = false
            wizardsRecyclerView.isVisible = false
        }
    }

    private fun hideLoading() {
        with(binding) {
            progressBar.isVisible = false
            heading.isVisible = true
            wizardsRecyclerView.isVisible = true
        }
    }
}