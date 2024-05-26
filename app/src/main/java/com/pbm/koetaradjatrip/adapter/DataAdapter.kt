package com.pbm.koetaradjatrip.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pbm.koetaradjatrip.databinding.ItemDataBinding
import com.pbm.koetaradjatrip.models.Data

class DataAdapter : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    private var dataList: List<Data> = emptyList()

    inner class DataViewHolder(private val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.textViewName.text = data.name
            binding.textViewDescription.text = data.description
            // Load gambar dari byte array menggunakan Glide
            Glide.with(binding.imageView.context)
                .load(data.image)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitList(list: List<Data>) {
        dataList = list
        notifyDataSetChanged()
    }
}
