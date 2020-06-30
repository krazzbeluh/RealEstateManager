package com.openclassrooms.realestatemanager.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.R

class MainDetailFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Show the dummy content as text in a TextView.

        return inflater.inflate(R.layout.fragment_main_detail, container, false)
    }

    companion object {
        const val ARG_ESTATE = "estate"
    }
}