package com.pbm.koetaradjatrip.halaman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pbm.koetaradjatrip.R
import com.pbm.koetaradjatrip.databinding.FragmentMapsBinding

class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var mMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        val gson = Gson()
        val jsonData = "[{\"name\":\"Hutan Ketambe\",\"latitude\":3.692492,\"longitude\":97.652375},{\"name\":\"Pantai Ulee Lheue\",\"latitude\":5.5597465,\"longitude\":95.2028563},{\"name\":\"Gua Sarang\",\"latitude\":5.8272424,\"longitude\":95.2496519},{\"name\":\"Air Terjun Kutamalaka\",\"latitude\":5.3951268,\"longitude\":95.3251822},{\"name\":\"Danau Laut Tawar\",\"latitude\":4.6120284,\"longitude\":96.8824114}]"
        val type = object : TypeToken<List<Place>>() {}.type
        val places: List<Place> = gson.fromJson(jsonData, type)

        for (place in places) {
            val location = LatLng(place.latitude, place.longitude)
            mMap.addMarker(MarkerOptions().position(location).title(place.name))
        }

        // Move camera to first location
        if (places.isNotEmpty()) {
            val firstLocation = LatLng(places[0].latitude, places[0].longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 10f))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    data class Place(
        val name: String,
        val latitude: Double,
        val longitude: Double
    )
}
