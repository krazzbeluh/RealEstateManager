package com.openclassrooms.realestatemanager.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate

class MainDetailFragment : androidx.fragment.app.Fragment() {
    lateinit var viewModel: MainDetailViewModel

    lateinit var estate: Estate

    lateinit var surfaceTextView: TextView

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
        view.findViewById<TextView>(R.id.main_detail_surface).apply {
            text = estate.area.toString()
        }
        view.findViewById<TextView>(R.id.main_detail_rooms).apply {
            text = estate.rooms.toString()
        }
        view.findViewById<TextView>(R.id.main_detail_price).apply {
            text = estate.price.toString()
        }
        view.findViewById<TextView>(R.id.main_detail_location).apply {
            text = estate.address.toFormattedAddress()
        }

        return view
    }

    companion object {
        const val ARG_ESTATE = "estate"
    }
}