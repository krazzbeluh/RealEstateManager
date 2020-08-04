package com.openclassrooms.realestatemanager.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.PhotosRecyclerViewAdapter

class EstateDetailFragment : androidx.fragment.app.Fragment() {
    companion object {
        const val ARG_ESTATE = "estate"
    }

    private lateinit var viewModel: MainDetailViewModel

    private lateinit var estate: Estate

    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainDetailViewModel::class.java)

        arguments?.let {
            estate = (it.getSerializable(ARG_ESTATE) as? Estate)
                    ?: throw TypeCastException("Can't convert Serializable to Estate")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_detail, container, false)
        view.apply {
            findViewById<TextView>(R.id.main_detail_description).apply {
                text = estate.estate.description
            }
            findViewById<TextView>(R.id.main_detail_surface).apply {
                text = estate.estate.area.toString()
            }
            findViewById<TextView>(R.id.main_detail_rooms).apply {
                text = estate.estate.rooms.toString()
            }
            findViewById<TextView>(R.id.main_detail_price).apply {
                text = estate.estate.price.toString()
            }
            findViewById<TextView>(R.id.main_detail_location).apply {
                text = estate.estate.address.format()
            }
            findViewById<TextView>(R.id.main_detail_poi).apply {
                text = estate.getPois()
            }
            findViewById<TextView>(R.id.main_detail_available).apply {
                text = if (estate.estate.sold) getString(R.string.no) else getString(R.string.yes)
            }
            findViewById<TextView>(R.id.main_detail_agent).apply {
                text = estate.estate.agent
            }
            findViewById<RecyclerView>(R.id.main_detail_photos).apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val adapter = PhotosRecyclerViewAdapter(context)
                adapter.setPhotos(estate.photos)
                this.adapter = adapter
            }
            mapView = findViewById<MapView>(R.id.mapView).apply {
                onCreate(savedInstanceState)
                val location = estate.estate.address.getLocation()
                if (location != null) {
                    getMapAsync { map ->
                        map.moveCamera(CameraUpdateFactory.newLatLng(location))
                        mapView?.onResume()
                    }
                    visibility = View.VISIBLE
                }
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}