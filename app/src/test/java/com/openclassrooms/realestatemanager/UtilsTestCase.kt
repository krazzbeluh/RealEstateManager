package com.openclassrooms.realestatemanager

import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.utils.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import kotlin.math.roundToInt

class UtilsTestCase {
    @Test
    fun testConversionToEuroShouldConvertToDollarValue() {
        val dollar = 153
        val expectedEuro = (dollar * 0.812).roundToInt()

        assertEquals(expectedEuro, dollar.convertToEuro())
    }

    @Test
    fun testConversionToDollarShouldConvertToEuroValue() {
        val euro = 153
        val expectedDollar = (euro / 0.812).roundToInt()

        assertEquals(expectedDollar, euro.convertToDollar())
    }

    @Test
    fun testGetTodayDateShouldReturnFormattedDate() {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val expectedValue = "${if (day < 10) "0$day" else "$day"}/${if (month < 10) "0$month" else "$month"}/${calendar.get(Calendar.YEAR)}"
        assertEquals(expectedValue, Utils.getTodayDate())
    }

    @Test
    fun testRoundShouldRoundEntries() {
        val number = 10.2345
        val expected = 10.23
        assertEquals(expected, number.round(2), 0.0)
    }

    @Test
    fun testDistanceWithShouldReturnRealDistance() {
        val latLng = LatLng(0.0, 0.0)
        val compared = LatLng(1.0, 1.0)
        assertEquals(157249, latLng.distanceWith(compared))
    }
}