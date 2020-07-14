package com.openclassrooms.realestatemanager.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class Estate(@PrimaryKey(autoGenerate = true) val id: Int?,
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

    override fun equals(other: Any?): Boolean {
        return if (other is Estate) {
            other.address == address
                    && other.type == type
                    && other.price == price
                    && other.rooms == rooms
                    && other.area == area
                    && other.description == description
                    && other.photos == photos
                    && other.nearbyPointsOfInterests == nearbyPointsOfInterests
                    && other.agent == agent
                    && other.sold == sold
        } else false
    }

    override fun hashCode(): Int {
        var result = address.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + price
        result = 31 * result + rooms
        result = 31 * result + area
        result = 31 * result + description.hashCode()
        result = 31 * result + photos.hashCode()
        result = 31 * result + nearbyPointsOfInterests.hashCode()
        result = 31 * result + agent.hashCode()
        result = 31 * result + sold.hashCode()
        return result
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