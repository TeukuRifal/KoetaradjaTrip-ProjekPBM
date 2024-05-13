package com.pbm.koetaradjatrip.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pbm.koetaradjatrip.R
import com.pbm.koetaradjatrip.models.Place

class PlaceAdapter(private val places: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeName: TextView = itemView.findViewById(R.id.place_name)
        private val placeDescription: TextView = itemView.findViewById(R.id.place_description)
        private val placeImage: ImageView = itemView.findViewById(R.id.place_image)

        fun bind(place: Place) {
            placeName.text = place.name
            placeDescription.text = place.description
            Glide.with(itemView.context)
                .load(place.imageUrl)
                .into(placeImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int {
        return places.size
    }
}
