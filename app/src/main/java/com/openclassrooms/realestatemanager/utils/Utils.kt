package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Philippe on 21/02/2018.
 */
object Utils {
    private val TAG = Utils::class.java.simpleName
    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars value in dollar to be converted
     * @return converted value in euro
     */
    fun convertDollarToEuro(dollars: Int): Int {
        return (dollars * 0.812).roundToInt()
    }

    fun convertEuroToDollar(euro: Int): Int {
        return (euro / 0.812).roundToInt()
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return today date formatted in yyyy/mm/dd
     */
    fun getTodayDate(context: Context): String {
        @Suppress("DEPRECATION") val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else context.resources.configuration.locale
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", locale)
        return dateFormat.format(Date())
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context context object
     * @return if internet is available
     */
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities)
                    ?: return false
            return actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            connectivityManager.run {
                @Suppress("DEPRECATION")
                connectivityManager.activeNetworkInfo?.run {
                    return type == ConnectivityManager.TYPE_WIFI
                            || type == ConnectivityManager.TYPE_MOBILE
                            || type == ConnectivityManager.TYPE_ETHERNET
                }
                return false
            }
        }
    }

    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        var gpsEnabled = false
        var networkEnabled = false

        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            Log.e(TAG, "isLocationEnabled: ", e)
        }

        return gpsEnabled || networkEnabled
    }
}