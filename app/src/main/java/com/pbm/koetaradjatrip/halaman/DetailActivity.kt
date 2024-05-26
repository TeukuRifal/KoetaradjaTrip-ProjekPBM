package com.pbm.koetaradjatrip.halaman

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.pbm.koetaradjatrip.R
import com.pbm.koetaradjatrip.models.Place

class DetailActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var place: Place
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        sharedPreferences = getSharedPreferences("Favorites", Context.MODE_PRIVATE)

        // Retrieve data from intent
        val placeName = intent.getStringExtra("EXTRA_PLACE_NAME")
        val placeDesc = intent.getStringExtra("EXTRA_PLACE_DESCRIPTION")
        val placeImageUrl = intent.getStringExtra("EXTRA_PLACE_IMAGE_URL")

        // Initialize views
        val ivPhoto: ImageView = findViewById(R.id.place_image)
        val titleDescription: TextView = findViewById(R.id.place_name)
        val placeDescription: TextView = findViewById(R.id.place_description)
        val favoriteButton: ImageButton = findViewById(R.id.favorite_button)

        // Set data to views
        titleDescription.text = placeName
        placeDescription.text = placeDesc
        Glide.with(this).load(placeImageUrl).into(ivPhoto)

        // Create a Place object
        place = Place(placeName ?: "", placeDesc ?: "", placeImageUrl ?: "")

        // Check if current place is favorite and set the favorite button accordingly
        favoriteButton.setImageResource(
            if (isFavorite(place)) R.drawable.fav_filled else R.drawable.fav_outlined
        )

        // Set click listener for the favorite button
        favoriteButton.setOnClickListener {
            if (isFavorite(place)) {
                removeFromFavorites(place)
                favoriteButton.setImageResource(R.drawable.fav_outlined)
            } else {
                addToFavorites(place)
                favoriteButton.setImageResource(R.drawable.fav_filled)
            }
        }
    }

    private fun isFavorite(place: Place): Boolean {
        val placeJson = sharedPreferences.getString(place.name, null)
        return placeJson != null
    }

    private fun addToFavorites(place: Place) {
        val editor = sharedPreferences.edit()
        val placeJson = gson.toJson(place)
        editor.putString(place.name, placeJson)
        editor.apply()
    }

    private fun removeFromFavorites(place: Place) {
        val editor = sharedPreferences.edit()
        editor.remove(place.name)
        editor.apply()
    }
}
