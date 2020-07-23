package com.openclassrooms.realestatemanager.model.estate

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable
import java.util.*

data class Estate(@Embedded var estate: SimpleEstate) : Serializable {
    @Relation(parentColumn = "id", entityColumn = "estateId", entity = AssociatedPOI::class)
    var nearbyPointsOfInterests = mutableListOf<AssociatedPOI>()

    fun getPois(): String {
        var s = ""

        for (poi in nearbyPointsOfInterests) {
            s += poi.poi.toString().toLowerCase(Locale.ROOT)
            if (poi != nearbyPointsOfInterests.last()) s += "\n"
        }

        return s
    }

    override fun equals(other: Any?) = other is Estate && estate == other.estate && nearbyPointsOfInterests == other.nearbyPointsOfInterests

    override fun hashCode(): Int {
        var result = estate.hashCode()
        result = 31 * result + nearbyPointsOfInterests.hashCode()
        return result
    }

    @Suppress("unused")
    enum class EstateType {
        FLAT,
        HOUSE,
        DUPLEX,
        PENTHOUSE
    }
}