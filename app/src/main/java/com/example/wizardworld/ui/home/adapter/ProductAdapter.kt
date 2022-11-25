package com.example.wizardworld.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wizardworld.databinding.ProductsListItemBinding
import com.example.wizardworld.domain.model.Product

class ProductAdapter :
    RecyclerView.Adapter<ProductItemViewHolder>() {
    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        return ProductItemViewHolder(
            ProductsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickListener
        )
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return true
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    interface OnClickListener {
        fun onClick(choice: Int)
    }
}
