package com.pbm.koetaradjatrip.halaman

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
import java.io.IOException

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search_page, container, false)

        // Initialize RecyclerView
        recyclerView = rootView.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Load and set data to RecyclerView
        val places = loadPlacesFromJson()
        placeAdapter = PlaceAdapter(places)
        recyclerView.adapter = placeAdapter

        return rootView
    }

    private fun loadPlacesFromJson(): List<Place> {
        val json: String
        val gson = Gson()
        val placesListType = object : com.google.gson.reflect.TypeToken<List<Place>>() {}.type
        try {
            val inputStream = requireActivity().assets.open("places.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            return emptyList()
        }

        return gson.fromJson(json, placesListType)
    }
}
