package com.openclassrooms.realestatemanager.database.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openclassrooms.realestatemanager.model.Estate

class POIConverter {
    @TypeConverter
    fun toPoi(s: String): List<Estate.POI> {
        val listPois = object : TypeToken<Array<Estate.POI>>() {}.type
        return (Gson().fromJson(s, listPois) as Array<Estate.POI>).toList()
    }

    @TypeConverter
    fun toString(pois: List<Estate.POI>): String {
        return Gson().toJson(pois)
    }
}