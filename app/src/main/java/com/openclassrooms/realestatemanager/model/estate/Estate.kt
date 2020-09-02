package com.openclassrooms.realestatemanager.model.estate

import android.content.ContentValues
import androidx.room.Embedded
import androidx.room.Relation
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Photo
import java.io.Serializable
import java.util.*

data class Estate(@Embedded var estate: SimpleEstate) : Serializable {
    @Relation(parentColumn = "id", entityColumn = "estateId", entity = AssociatedPOI::class)
    var nearbyPointsOfInterests = mutableListOf<AssociatedPOI>()

    @Relation(parentColumn = "id", entityColumn = "estateId", entity = Photo::class)
    var photos: List<Photo> = listOf()

    fun getPois(): String {
        var s = ""

        for (poi in nearbyPointsOfInterests) {
            s += poi.poi.toString().toLowerCase(Locale.ROOT)
            if (poi != nearbyPointsOfInterests.last()) s += "\n"
        }

        return s
    }

    override fun equals(other: Any?) = other is Estate
            && estate == other.estate
            && nearbyPointsOfInterests == other.nearbyPointsOfInterests

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

    companion object {
        fun fromContentValues(values: ContentValues): Estate {
            values.apply {
                val address = Address(
                        getAsInteger("addressNumber"),
                        getAsString("addressRoute"),
                        getAsString("addressCity"),
                        getAsInteger("addressPostCode"),
                        getAsString("addressCountry")
                )
                val type = EstateType.valueOf(getAsString("estateType"))
                val price = getAsInteger("estatePrice")
                val rooms = getAsInteger("estateRooms")
                val area = getAsInteger("estateArea")
                val description = getAsString("estateDescription")
                val agent = getAsString("estateAgent")
                val sold = getAsBoolean("isEstateSold")

                return Estate(SimpleEstate(0, address, type, price, rooms, area, description, agent, sold))
            }
        }
    }
}