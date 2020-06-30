package com.openclassrooms.realestatemanager.model

import android.graphics.Bitmap

data class Estate(val id: Int, val address: Address, val type: EstateType, val price: Int,
                  val rooms: Int, val area: Int, val description: String, val photos: List<Photo>,
                  val nearbyPointsOfInterests: List<POI>, val agent: String, val sold: Boolean = false) {

    enum class EstateType {
        FLAT,
        HOUSE,
        DUPLEX,
        PENTHOUSE
    }

    enum class POI {
        SCHOOL,
        PARK,
        SUPERMARKET
    }
}