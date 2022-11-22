package com.example.wizardworld.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.wizardworld.databinding.DetailListItemBinding

class DetailItemViewHolder(private val binding: DetailListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(productData: String) {
        binding.productNameTv.text = productData
    }
}