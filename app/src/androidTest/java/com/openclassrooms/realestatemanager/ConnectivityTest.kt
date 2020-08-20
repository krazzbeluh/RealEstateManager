package com.openclassrooms.realestatemanager

import android.content.Context
import android.location.LocationManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.utils.NetworkUtils
import com.openclassrooms.realestatemanager.utils.Utils
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when` as on

@RunWith(AndroidJUnit4::class)
class ConnectivityTest {
    @Test
    fun testIsInternetAvailableShouldReturnFalseIfNoConnectionAvailable() {
        val networkUtils = mock(NetworkUtils::class.java).apply {
            on(hasTransport(any(), anyInt())).thenReturn(false)
        }

        assertFalse(Utils.isInternetAvailable(networkUtils, InstrumentationRegistry.getInstrumentation().targetContext))
    }

    @Test
    fun testIsInternetAvailableShouldReturnTrueIfAnyConnectionAvailable() {
        val networkUtils = mock(NetworkUtils::class.java).apply {
            on(hasTransport(any(), anyInt())).thenReturn(true)
        }

        assertTrue(Utils.isInternetAvailable(networkUtils, InstrumentationRegistry.getInstrumentation().targetContext))
    }

    @Test
    fun testIsLocationEnabledShouldReturnFalseIfNothingEnabled() {
        val locationManager = mock(LocationManager::class.java).apply {
            on(isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false)
            on(isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(false)
        }
        val context = mock(Context::class.java).apply {
            on(applicationContext).thenReturn(this)
            on(getSystemService(Context.LOCATION_SERVICE)).thenReturn(locationManager)
        }

        assertFalse(Utils.isLocationEnabled(context))
    }
}