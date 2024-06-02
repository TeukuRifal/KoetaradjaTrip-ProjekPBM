package com.pbm.koetaradjatrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pbm.koetaradjatrip.databinding.ItemDataBinding
import com.pbm.koetaradjatrip.models.Data

class DataAdapter(private val onDeleteClick: (Data) -> Unit) : PagingDataAdapter<Data, DataAdapter.DataViewHolder>(DiffCallback()) {

    inner class DataViewHolder(private val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.textViewName.text = data.name
            binding.textViewDescription.text = data.description
            Glide.with(binding.imageView.context)
                .load(data.image)
                .into(binding.imageView)

            binding.delete.setOnClickListener {
                onDeleteClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}
