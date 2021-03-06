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

const val PREFERENCES_NAME = "GLOBAL_PREFERENCES"
const val IS_DOLLAR = "IS_DOLLAR_UNIT"

fun Int.convertToEuro() = (this * 0.812).roundToInt()
fun Int.convertToDollar() = (this / 0.812).roundToInt()

object Utils {
    private val TAG = Utils::class.java.simpleName

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return today date formatted in dd/mm/yyyy
     */
    fun getTodayDate(): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context context object
     * @return if internet is available
     */
    fun isInternetAvailable(networkUtils: NetworkUtils, context: Context): Boolean {
        val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            networkUtils.apply {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                        ?: return false
                return hasTransport(networkCapabilities, NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(networkCapabilities, NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(networkCapabilities, NetworkCapabilities.TRANSPORT_ETHERNET)
            }
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