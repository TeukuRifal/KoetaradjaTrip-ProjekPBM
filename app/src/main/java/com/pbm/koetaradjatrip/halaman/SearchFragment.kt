package com.pbm.koetaradjatrip.halaman

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.pbm.koetaradjatrip.R
import com.pbm.koetaradjatrip.adapter.PlaceAdapter
import com.pbm.koetaradjatrip.models.Place
import com.google.gson.Gson
import java.io.IOException

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var editTextSearch: EditText
    private lateinit var textInputLayout: TextInputLayout

    private var places: List<Place> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search_page, container, false)

        // Initialize RecyclerView
        recyclerView = rootView.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Load and set data to RecyclerView
        places = loadPlacesFromJson()
        placeAdapter = PlaceAdapter(places)
        recyclerView.adapter = placeAdapter

        // Initialize search components
        textInputLayout = rootView.findViewById(R.id.text_input_layout_search)
        editTextSearch = rootView.findViewById(R.id.edit_text_search)

        // Add TextWatcher to EditText for dynamic search
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                searchPlaces(query)
            }
        })

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

    private fun searchPlaces(query: String) {
        val filteredPlaces = places.filter { place ->
            place.name.contains(query, true)
        }
        placeAdapter.updatePlaces(filteredPlaces)
    }
}
