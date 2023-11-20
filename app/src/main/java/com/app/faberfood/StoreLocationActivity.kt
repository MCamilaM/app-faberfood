package com.app.faberfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.app.faberfood.databinding.ActivityStoreLocationBinding

class StoreLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    private lateinit var binding: ActivityStoreLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoreLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        // Add a marker in Sydney and move the camera
        val andino = LatLng(4.666882127064064, -74.05258006015822)
        mGoogleMap!!.addMarker(MarkerOptions().position(andino).title("Faborfood - Tercer piso"))
        mGoogleMap!!.animateCamera(
            CameraUpdateFactory.newLatLngZoom(andino, 18f),
            4000,
            null
        )

    }
}