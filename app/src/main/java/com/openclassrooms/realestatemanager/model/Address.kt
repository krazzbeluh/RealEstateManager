package com.openclassrooms.realestatemanager.model

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class Address(val number: Int, val route: String, val city: String, val postCode: Int, val country: String, var lat: Double? = null, var lng: Double? = null) : Serializable {

    fun format(): String {
        return "$number $route" +
                "\n$postCode" +
                "\n$city" +
                "\n$country"
    }

    fun getLocation(): LatLng? = if (lat != null && lng != null) LatLng(lat!!, lng!!) else null
    fun setLocation(location: LatLng) {
        this.lat = location.latitude
        this.lng = location.longitude
    }

    override fun toString(): String = "$number $route, $postCode $city, $country"
}