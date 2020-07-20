package com.openclassrooms.realestatemanager.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate

class MainDetailFragment : androidx.fragment.app.Fragment() {
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

        view.findViewById<TextView>(R.id.main_detail_description).apply {
            text = estate.description
        }
        /*view.findViewById<TextView>(R.id.main_detail_surface).apply {
            text = estate.area.toString()
        }
        view.findViewById<TextView>(R.id.main_detail_rooms).apply {
            text = estate.rooms.toString()
        }
        view.findViewById<TextView>(R.id.main_detail_price).apply {
            text = estate.price.toString()
        }
        view.findViewById<TextView>(R.id.main_detail_location).apply {
            text = estate.address.format()
        }
        view.findViewById<TextView>(R.id.main_detail_poi).apply {
            text = estate.getPois()
        }
        view.findViewById<TextView>(R.id.main_detail_available).apply {
            text = if (estate.sold) getString(R.string.no) else getString(R.string.yes)
        }
        view.findViewById<TextView>(R.id.main_detail_agent).apply {
            text = estate.agent
        }*/
        mapView = view.findViewById<MapView>(R.id.mapView).apply {
            onCreate(savedInstanceState)
            getMapAsync { map ->
                map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(40.7005, -73.9882))) // TODO: set real location
                mapView?.onResume()
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