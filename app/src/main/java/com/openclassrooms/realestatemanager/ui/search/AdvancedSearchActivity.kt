package com.openclassrooms.realestatemanager.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jaygoo.widget.RangeSeekBar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment.Companion.ARG_ESTATE
import com.openclassrooms.realestatemanager.ui.search.result.SearchResultListActivity
import java.io.Serializable

class AdvancedSearchActivity : AppCompatActivity() {
    companion object {
        @Suppress("unused")
        private val TAG = AdvancedSearchActivity::class.java.simpleName
    }

    private lateinit var viewModel: AdvancedSearchViewModel

    private lateinit var priceRangeSeekBar: RangeSeekBar
    private lateinit var roomsRangeSeekBar: RangeSeekBar
    private lateinit var surfaceRangeSeekBar: RangeSeekBar
    private lateinit var cityEditText: EditText
    private lateinit var distanceEditText: EditText
    private lateinit var orderBySpinner: Spinner
    private lateinit var orderCheckBox: CheckBox

    private var estates = listOf<Estate>()

    private var priceRange = 0..10000000
    private var roomsRange = 0..20
    private var surfaceRange = 0..250

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_search)

        priceRangeSeekBar = findViewById(R.id.advanced_search_price_range_seekbar)
        roomsRangeSeekBar = findViewById(R.id.advanced_search_rooms_range_seekbar)
        surfaceRangeSeekBar = findViewById(R.id.advanced_search_surface_range_seekbar)
        cityEditText = findViewById(R.id.advanced_search_city)
        distanceEditText = findViewById(R.id.advanced_search_distance)
        orderBySpinner = findViewById(R.id.advanced_search_order_spinner)
        orderCheckBox = findViewById(R.id.advnced_search_order_checkbox)
        configureInputs()

        val viewModelFactory = Injection.provideViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AdvancedSearchViewModel::class.java)

        viewModel.getEstates().observe(this, Observer { estates ->
            this.estates = estates
            if (estates.size <= 1) return@Observer

            val minPrice = estates.minBy { it.estate.price }?.estate?.price ?: 0
            val maxPrice = estates.maxBy { it.estate.price }?.estate?.price?.plus(1) ?: 10000000
            priceRange = minPrice..maxPrice

            val minRooms = estates.minBy { it.estate.rooms }?.estate?.rooms ?: 0
            val maxRooms = estates.maxBy { it.estate.rooms }?.estate?.rooms?.plus(1) ?: 20
            roomsRange = minRooms..maxRooms

            val minSurface = estates.minBy { it.estate.area }?.estate?.area ?: 0
            val maxSurface = estates.maxBy { it.estate.area }?.estate?.area?.plus(1) ?: 250
            surfaceRange = minSurface..maxSurface

            configureInputs()
        })
    }

    private fun configureInputs() {
        configurePriceSeekBar()
        configureRoomsSeekBar()
        configureSurfaceSeekBar()
        configureOrderInputs()
    }

    private fun configurePriceSeekBar() {
        priceRangeSeekBar.setRange(priceRange.first.toFloat(), priceRange.last.toFloat())
        priceRangeSeekBar.setValue(priceRange.first.toFloat(), priceRange.last.toFloat())
    }

    private fun configureRoomsSeekBar() {
        roomsRangeSeekBar.setRange(roomsRange.first.toFloat(), roomsRange.last.toFloat())
        roomsRangeSeekBar.setValue(roomsRange.first.toFloat(), roomsRange.last.toFloat())
    }

    private fun configureSurfaceSeekBar() {
        surfaceRangeSeekBar.setRange(surfaceRange.first.toFloat(), surfaceRange.last.toFloat())
        surfaceRangeSeekBar.setValue(surfaceRange.first.toFloat(), surfaceRange.last.toFloat())
    }

    private fun configureOrderInputs() {
        orderBySpinner.adapter = ArrayAdapter<EstateOrderField>(this, android.R.layout.simple_list_item_1, EstateOrderField.values())
        orderCheckBox.setOnCheckedChangeListener { _, isChecked -> orderCheckBox.setText(if (isChecked) R.string.ascending else R.string.descending) }
    }

    fun onClickOnSearchButton(@Suppress("UNUSED_PARAMETER") v: View) {
        val orderBy = orderBySpinner.selectedItem as? EstateOrderField
        val filteredEstates = viewModel.search(estates,
                priceRangeSeekBar.currentRange[0].toInt()..priceRangeSeekBar.currentRange[1].toInt(),
                surfaceRangeSeekBar.currentRange[0].toInt()..surfaceRangeSeekBar.currentRange[1].toInt(),
                roomsRangeSeekBar.currentRange[0].toInt()..roomsRangeSeekBar.currentRange[1].toInt(),
                orderBy ?: EstateOrderField.PRICE,
                orderCheckBox.isChecked)

        val intent = Intent(this, SearchResultListActivity::class.java)
        intent.putExtra(ARG_ESTATE, filteredEstates as? Serializable)
        startActivity(intent)
    }
}