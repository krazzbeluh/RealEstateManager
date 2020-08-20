package com.openclassrooms.realestatemanager.utils

import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

open class NetworkUtils {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    open fun hasTransport(networkCapabilities: NetworkCapabilities?, i: Int) = networkCapabilities?.hasTransport(i)
            ?: false
}