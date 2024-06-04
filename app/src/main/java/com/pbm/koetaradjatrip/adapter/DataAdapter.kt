package com.pbm.koetaradjatrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pbm.koetaradjatrip.databinding.ItemDataBinding
import com.pbm.koetaradjatrip.models.Data
import androidx.paging.PagingDataAdapter
import java.io.File

class DataAdapter(private val onItemClickListener: OnItemClickListener) : PagingDataAdapter<Data, DataAdapter.DataViewHolder>(DiffCallback()) {

    inner class DataViewHolder(private val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.textViewName.text = data.name
            binding.textViewDescription.text = data.description
            // Load gambar dari path menggunakan Glide
            Glide.with(binding.imageView.context)
                .load(File(data.imagePath))
                .into(binding.imageView)

            // Set onClickListener untuk setiap item
            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(data)
            }

            // Set onClickListener untuk tombol delete
            binding.delete.setOnClickListener {
                onItemClickListener.onDeleteClick(data)
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

    interface OnItemClickListener {
        fun onItemClick(data: Data)
        fun onDeleteClick(data: Data)
    }
}

