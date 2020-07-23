package com.openclassrooms.realestatemanager.database.typeconverter

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.model.estate.AssociatedPOI

class POIConverter {
    @TypeConverter
    fun toPoi(s: String): AssociatedPOI.POI {
        return AssociatedPOI.POI.valueOf(s)
    }

    @TypeConverter
    fun toString(pois: AssociatedPOI.POI): String {
        return pois.toString()
    }
}