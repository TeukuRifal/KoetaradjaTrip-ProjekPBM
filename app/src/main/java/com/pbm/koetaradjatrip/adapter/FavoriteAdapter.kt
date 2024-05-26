package com.pbm.koetaradjatrip.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pbm.koetaradjatrip.R

class FavoriteAdapter(private val favoritePlaces: List<String>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeNameTextView: TextView = itemView.findViewById(R.id.place_name)

        fun bind(placeName: String) {
            placeNameTextView.text = placeName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_place, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val placeName = favoritePlaces[position]
        holder.bind(placeName)
    }

    override fun getItemCount(): Int {
        return favoritePlaces.size
    }
}
