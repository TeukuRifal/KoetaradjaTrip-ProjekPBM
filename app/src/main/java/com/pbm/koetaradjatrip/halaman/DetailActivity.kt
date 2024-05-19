package com.pbm.koetaradjatrip.halaman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.pbm.koetaradjatrip.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val ivPhoto: ImageView = findViewById(R.id.ivphoto)
        val titleDescription: TextView = findViewById(R.id.titleDescription)
        val placeDescription: TextView = findViewById(R.id.placeDescription)

        val placeName = intent.getStringExtra("EXTRA_PLACE_NAME")
        val placeDesc = intent.getStringExtra("EXTRA_PLACE_DESCRIPTION")
        val placeImageUrl = intent.getStringExtra("EXTRA_PLACE_IMAGE_URL")

        titleDescription.text = placeName
        placeDescription.text = placeDesc

        Glide.with(this)
            .load(placeImageUrl)
            .into(ivPhoto)
    }
}
