package com.pbm.koetaradjatrip.halaman

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pbm.koetaradjatrip.R
import com.pbm.koetaradjatrip.databinding.FragmentMapsBinding
import com.pbm.koetaradjatrip.databinding.MarkerInfoBinding
import java.io.BufferedReader
import java.io.InputStreamReader

class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var places: List<Place>

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        // Enable zoom controls and gestures
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.uiSettings.isScrollGesturesEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = true

        val gson = Gson()

        // Load JSON from assets
        val jsonData = loadJSONFromAsset(requireContext(), "places.json")
        val type = object : TypeToken<List<Place>>() {}.type
        places = gson.fromJson(jsonData, type)

        for (place in places) {
            val location = LatLng(place.latitude, place.longitude)
            mMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(place.name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)) // Custom marker icon
            )
        }

        // Move camera to user's location if available
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val userLatLng = LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 10f))
                }
            }
        }

        // Set marker click listener
        mMap.setOnMarkerClickListener { marker ->
            showMarkerInfo(marker)
            true
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.btnZoomIn.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomIn())
        }

        binding.btnZoomOut.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }

    private fun loadJSONFromAsset(context: Context, fileName: String): String {
        val jsonString: String
        try {
            val inputStream = context.assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            jsonString = bufferedReader.use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
        return jsonString
    }

    private fun showMarkerInfo(marker: Marker) {
        val place = places.find { it.name == marker.title }
        place?.let {
            val dialogBinding = MarkerInfoBinding.inflate(LayoutInflater.from(requireContext()))
            dialogBinding.markerTitle.text = it.name
            Glide.with(this).load(it.imageUrl).into(dialogBinding.markerImage)

            AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .setPositiveButton("Close", null)
                .show()
        }
    }

    data class Place(
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val imageUrl: String
    )
}
