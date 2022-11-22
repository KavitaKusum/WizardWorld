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
import com.example.wizardworld.R
import com.example.wizardworld.databinding.FragmentWizardsBinding
import com.example.wizardworld.presentation.ProductListViewModel
import com.example.wizardworld.ui.home.HomeActivity
import com.example.wizardworld.ui.home.adapter.ProductListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentWizardsBinding
    private val viewModel: ProductListViewModel by viewModels()
    private val productListAdapter by lazy{ ProductListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as HomeActivity).binding.imgBack.isVisible = true
        var choice: Int
        arguments.let {
            choice= it?.getInt("position") ?: 0
        }
        binding = FragmentWizardsBinding.inflate(layoutInflater)
        binding.wizardsRecyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            productListAdapter.onClickListener = object :
                ProductListAdapter.OnClickListener {
                override fun onClick(productId: String) {
                    when(choice){
                        1->findNavController().navigate(R.id.action_to_WizardDetailsFragment, bundleOf("productId" to productId))
                        2->findNavController().navigate(R.id.action_to_HouseDetailsFragment, bundleOf("productId" to productId))
                        3->findNavController().navigate(R.id.action_to_ElixirDetailsFragment, bundleOf("productId" to productId))
                        4->findNavController().navigate(R.id.action_to_SpellDetailsFragment, bundleOf("productId" to productId))
                    }
                }
            }
            adapter = productListAdapter
        }
        viewModel.getProductList(choice,requireContext())
        lifecycleScope.launch {
            viewModel.viewState.collect { viewState ->
                if(viewState.isLoading) showLoading()
                viewState.error?.let {
                    hideLoading()
                    Toast.makeText(requireContext(), getString(R.string.error_text, viewState.error), Toast.LENGTH_SHORT).show()
                }
                viewState.data?.let {
                    hideLoading()
                    setViews(choice,it)
                }
            }
        }
        return binding.root
    }

    private fun setViews(choice:Int,data: List<Triple<String, String, String>>) {
        hideLoading()
        productListAdapter.differ.submitList(data)
        binding.heading.text = viewModel.getHeadingText(choice,requireContext())
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            heading.isVisible=false
            wizardsRecyclerView.isVisible=false
        }
    }

    private fun hideLoading() {
        with(binding) {
            progressBar.isVisible = false
            heading.isVisible=true
            wizardsRecyclerView.isVisible=true
        }
    }
}