package com.example.wizardworld.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wizardworld.databinding.WizardListItemBinding

class ProductListAdapter :
    RecyclerView.Adapter<ProductListItemViewHolder>() {

    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListItemViewHolder {
        return ProductListItemViewHolder(
            WizardListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickListener
        )
    }

    override fun onBindViewHolder(holder: ProductListItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = differ.currentList.size

    interface OnClickListener {
        fun onClick(productId: String)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Triple<String, String, String>>() {
        override fun areItemsTheSame(
            oldItem: Triple<String, String, String>,
            newItem: Triple<String, String, String>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Triple<String, String, String>,
            newItem: Triple<String, String, String>
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
}