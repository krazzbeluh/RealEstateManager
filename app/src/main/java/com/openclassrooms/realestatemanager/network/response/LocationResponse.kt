package com.openclassrooms.realestatemanager.network.response

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LocationResponse {
    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("geocoding")
    @Expose
    var geocoding: Geocoding? = null

    @SerializedName("features")
    @Expose
    var features: List<Feature>? = null

    fun getLatLnt(): LatLng {
        val latLng = features?.get(0)?.geometry?.coordinates
        val lat = latLng?.get(0)
        val lng = latLng?.get(1)

        if (lat == null || lng == null) throw NullPointerException("Lat or Lng is null in OSM call")
        else return LatLng(lat, lng)
    }

    fun isValid() = features != null && features!!.isNotEmpty()
}