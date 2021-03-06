package com.openclassrooms.realestatemanager.model

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

open class Address(val number: Int, val route: String, val city: String, val postCode: Int, val country: String, private var lat: Double? = null, private var lng: Double? = null) : Serializable {
    fun format(): String {
        return "$number $route" +
                "\n$postCode" +
                "\n$city" +
                "\n$country"
    }

    fun getLocation(): LatLng? = if (lat != null && lng != null) LatLng(lat!!, lng!!) else null
    fun setLocation(location: LatLng?) {
        this.lat = location?.latitude
        this.lng = location?.longitude
    }

    fun getLat(): Double? {
        return lat
    }

    fun getLng(): Double? {
        return lng
    }

    override fun toString(): String = "$number $route, $postCode $city, $country"

    override fun hashCode(): Int {
        var result = number
        result = 31 * result + route.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + postCode
        result = 31 * result + country.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        return other is Address
                && number == other.number
                && route == other.route
                && city == other.city
                && postCode == other.postCode
                && country == other.country
    }
}