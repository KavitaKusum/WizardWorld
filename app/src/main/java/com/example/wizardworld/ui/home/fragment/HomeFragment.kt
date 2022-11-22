package com.example.wizardworld.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wizardworld.R
import com.example.wizardworld.databinding.FragmentHomeBinding
import com.example.wizardworld.presentation.HomeViewModel
import com.example.wizardworld.ui.home.HomeActivity
import com.example.wizardworld.ui.home.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(): Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val productAdapter by lazy{ ProductAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setViews()
        return binding.root
    }

    private fun setViews() {
        (requireActivity() as HomeActivity).binding.imgBack.isVisible = false
        binding.productsRecyclerView.apply {
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager
            productAdapter.onClickListener = object : ProductAdapter.OnClickListener {
                override fun onClick(productId: Int) {
                    findNavController().navigate(R.id.action_home_to_ProductsListFragment, bundleOf("position" to productId))
                }
            }
            adapter = productAdapter
        }
        productAdapter.differ.submitList(viewModel.getProductList(requireContext()))
        //productAdapter.setData(viewModel.getProductList(requireContext()))
    }
}