package com.openclassrooms.realestatemanager.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class Estate(@PrimaryKey(autoGenerate = true) val id: Int,
                  @Embedded val address: Address,
                  val type: EstateType,
                  val price: Int,
                  val rooms: Int,
                  val area: Int,
                  val description: String,
                  val photos: List<Photo>,
                  val nearbyPointsOfInterests: List<POI>,
                  val agent: String,
                  val sold: Boolean = false) : Serializable {

    fun getPois(): String {
        var s = ""

        for (poi in nearbyPointsOfInterests) {
            s += poi.toString().toLowerCase(Locale.ROOT)
            if (poi != nearbyPointsOfInterests.last()) s += "\n"
        }

        return s
    }

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