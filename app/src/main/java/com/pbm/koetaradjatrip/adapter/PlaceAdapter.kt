package com.pbm.koetaradjatrip.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.pbm.koetaradjatrip.R
import com.pbm.koetaradjatrip.models.Place
import com.pbm.koetaradjatrip.halaman.DetailActivity

class PlaceAdapter(private var places: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeName: TextView = itemView.findViewById(R.id.place_name)
        private val placeDescription: TextView = itemView.findViewById(R.id.place_description)
        private val placeImage: ImageView = itemView.findViewById(R.id.place_image)
        private val favoriteButton: ImageButton = itemView.findViewById(R.id.favorite_button)

        fun bind(place: Place, context: Context) {
            placeName.text = place.name
            placeDescription.text = place.description
            Glide.with(context)
                .load(place.imageUrl)
                .into(placeImage)

            // Set favorite button state based on SharedPreferences
            favoriteButton.setImageResource(
                if (isFavorite(place.name)) R.drawable.fav_filled else R.drawable.fav_outlined
            )

            // Set click listener for the list item
            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("EXTRA_PLACE_NAME", place.name)
                    putExtra("EXTRA_PLACE_DESCRIPTION", place.description)
                    putExtra("EXTRA_PLACE_IMAGE_URL", place.imageUrl)
                }
                context.startActivity(intent)
            }

            // Set click listener for the favorite button
            favoriteButton.setOnClickListener {
                val isFavorite = isFavorite(place.name)
                if (isFavorite) {
                    removeFromFavorites(place.name)
                } else {
                    addToFavorites(place)
                }
                // Update favorite button state after clicking
                favoriteButton.setImageResource(
                    if (isFavorite) R.drawable.fav_outlined else R.drawable.fav_filled
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        sharedPreferences = parent.context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    fun updatePlaces(newPlaces: List<Place>) {
        places = newPlaces
        notifyDataSetChanged()
    }

    private fun isFavorite(placeName: String): Boolean {
        return sharedPreferences.contains(placeName)
    }

    private fun addToFavorites(place: Place) {
        val editor = sharedPreferences.edit()
        val placeJson = gson.toJson(place)
        editor.putString(place.name, placeJson)
        editor.apply()
        println("Added to favorites: $placeJson") // Log data yang ditambahkan
    }

    private fun removeFromFavorites(placeName: String) {
        val editor = sharedPreferences.edit()
        editor.remove(placeName)
        editor.apply()
        println("Removed from favorites: $placeName") // Log data yang dihapus
    }
}