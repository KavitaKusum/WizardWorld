package com.example.wizardworld.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.wizardworld.databinding.ProductsListItemBinding
import com.example.wizardworld.domain.model.Product

class ProductItemViewHolder(
    private val binding: ProductsListItemBinding,
    private val onClickListener: ProductAdapter.OnClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(productData: Product) {
        with(binding){
            productCard.setOnClickListener {
                onClickListener.onClick(productData.productId)
            }
            productNameTv.text = productData.description
            when {
                productData.image!=0 -> {
                    productImageView.setImageResource(productData.image)
                    productImageView.clipToOutline = true
                }
            }
        }
    }
}