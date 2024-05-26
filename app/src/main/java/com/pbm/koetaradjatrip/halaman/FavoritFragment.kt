package com.pbm.koetaradjatrip.halaman

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pbm.koetaradjatrip.R
import com.pbm.koetaradjatrip.adapter.PlaceAdapter
import com.pbm.koetaradjatrip.models.Place

class FavoritFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var placeAdapter: PlaceAdapter
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        placeAdapter = PlaceAdapter(getFavoritePlaces())

        val recyclerView: RecyclerView = view.findViewById(R.id.favorite_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = placeAdapter
    }

    private fun getFavoritePlaces(): List<Place> {
        val favoritePlaces = mutableListOf<Place>()
        val allEntries = sharedPreferences.all

        for ((key, value) in allEntries) {
            val placeJson = value as? String
            if (placeJson != null) {
                try {
                    val place: Place = gson.fromJson(placeJson, Place::class.java)
                    favoritePlaces.add(place)
                } catch (e: Exception) {
                    e.printStackTrace() // Log error jika terjadi masalah parsing
                }
            }
        }

        return favoritePlaces
    }
}
