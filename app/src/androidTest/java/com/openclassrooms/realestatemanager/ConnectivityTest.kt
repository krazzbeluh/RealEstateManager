package com.openclassrooms.realestatemanager

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openclassrooms.realestatemanager.utils.Utils
import junit.framework.TestCase.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when` as on

@RunWith(AndroidJUnit4::class)
class ConnectivityTest {
    @Test // todo : ask Nicolas : networkCapabilities is final class
    fun testIsInternetAvailableShouldReturnFalseIfNoConnectionAvailable() {
        val networkCapabilities = mock(NetworkCapabilities::class.java).apply {
            on(hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(false)
            on(hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)).thenReturn(false)
            on(hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)).thenReturn(false)
        }
        val network = mock(Network::class.java)
        val connectivityManager = mock(ConnectivityManager::class.java).apply {
            on(activeNetwork).thenReturn(network)
            on(getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        }
        val context = mock(Context::class.java).apply {
            on(applicationContext.getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager).thenReturn(connectivityManager)
        }

        assertFalse(Utils.isInternetAvailable(context))
    }

    @Test
    fun testIsLocationEnabledShouldReturnFalseIfNothingEnabled() {
        val locationManager = mock(LocationManager::class.java).apply {
            on(isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false)
            on(isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(false)
        }
        val context = mock(Context::class.java).apply {
            on(applicationContext.getSystemService(Context.LOCATION_SERVICE)).thenReturn(locationManager)
        }

        assertFalse(Utils.isLocationEnabled(context))
    }
}