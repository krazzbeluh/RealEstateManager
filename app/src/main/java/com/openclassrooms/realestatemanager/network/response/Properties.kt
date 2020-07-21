package com.openclassrooms.realestatemanager.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Properties {
    @SerializedName("geocoding")
    @Expose
    var geocoding: Geocoding_? = null

}