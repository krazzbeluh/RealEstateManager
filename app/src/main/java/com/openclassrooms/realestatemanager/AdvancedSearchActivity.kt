package com.openclassrooms.realestatemanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaygoo.widget.RangeSeekBar

class AdvancedSearchActivity : AppCompatActivity() {
    private lateinit var priceRangeSeekBar: RangeSeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_search)

        configurePriceSeekBar()
    }

    private fun configurePriceSeekBar() {
        priceRangeSeekBar = findViewById(R.id.advanced_search_price_range_seekbar)
    }
}