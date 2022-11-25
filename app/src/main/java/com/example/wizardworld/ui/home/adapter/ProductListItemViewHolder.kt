package com.example.wizardworld.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.wizardworld.databinding.WizardListItemBinding

class ProductListItemViewHolder(
    private val binding: WizardListItemBinding,
    private val onClickListener: ProductListAdapter.OnClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(wizardData: Triple<String, String, String>) {
        with(binding) {
            wizardCard.setOnClickListener { onClickListener.onClick(wizardData.first) }
            wizardName.text = wizardData.second
            wizardDesc.text = wizardData.third
        }
    }
}
