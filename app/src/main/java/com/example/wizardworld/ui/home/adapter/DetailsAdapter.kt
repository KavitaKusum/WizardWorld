package com.example.wizardworld.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wizardworld.databinding.DetailListItemBinding

class DetailsAdapter :
    RecyclerView.Adapter<DetailItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemViewHolder {
        return DetailItemViewHolder(
            DetailListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = differ.currentList.size
    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}
