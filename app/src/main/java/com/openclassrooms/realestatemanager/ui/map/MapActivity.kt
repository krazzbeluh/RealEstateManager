package com.openclassrooms.realestatemanager.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment.Companion.ARG_ESTATE
import com.openclassrooms.realestatemanager.ui.main.detail.MainDetailActivity
import pub.devrel.easypermissions.EasyPermissions

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, LocationListener {
    companion object {
        private val TAG = MapActivity::class.java.simpleName

        private const val MIN_TIME = 400L
        private const val MIN_DISTANCE = 500F
    }

    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private lateinit var viewModel: MapViewModel
    private var estates = listOf<Estate>()
    private var markerEstateAssociation = mapOf<Marker, Estate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val viewModelFactory = Injection.provideViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)

        mapView = findViewById<MapView>(R.id.map_map).apply {
            getMapAsync(this@MapActivity)
            onCreate(savedInstanceState)
        }

        try {
            MapsInitializer.initialize(this)
        } catch (e: Exception) {
            Log.e(TAG, "onCreate: ", e)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            googleMap.isMyLocationEnabled = true
            googleMap.setOnInfoWindowClickListener(this)

            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this)
        }

        viewModel.getEstates().observe(this, Observer { estates ->
            map.clear()
            this.estates = estates
            val markerEstateAssociation = mutableMapOf<Marker, Estate>()
            for (estate in estates) {
                val location = estate.estate.address.getLocation()
                if (location != null) {
                    val markerOptions = MarkerOptions()
                            .title(estate.estate.description)
                            .position(location)
                    markerEstateAssociation[map.addMarker(markerOptions)] = estate
                }
            }
            this.markerEstateAssociation = markerEstateAssociation
        })

        mapView.onResume()
    }

    override fun onInfoWindowClick(marker: Marker) {
        val estate = markerEstateAssociation[marker]
        if (estate != null) {
            val intent = Intent(this, MainDetailActivity::class.java)
            intent.putExtra(ARG_ESTATE, estate)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    private var hasFocusedLocation = false
    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                            location.latitude,
                            location.longitude
                    ),
                    15F
            ))

            hasFocusedLocation = true
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}
}