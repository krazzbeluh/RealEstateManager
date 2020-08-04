package com.openclassrooms.realestatemanager.utils

import com.google.android.gms.maps.model.LatLng
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun LatLng.distanceWith(other: LatLng): Int {
    val earthRadius = 6371 // radius of earth in Km (Type1, Type2) -> TypeR in {}

    val lat1: Double = this.latitude
    val lat2: Double = other.latitude
    val lon1: Double = this.longitude
    val lon2: Double = other.longitude
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = (sin(dLat / 2) * sin(dLat / 2)
            + (cos(Math.toRadians(lat1))
            * cos(Math.toRadians(lat2)) * sin(dLon / 2)
            * sin(dLon / 2)))
    val c = 2 * asin(sqrt(a))

    return (earthRadius * c * 1000).toInt()
}