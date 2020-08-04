package com.openclassrooms.realestatemanager.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Feature {
    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null
}