package com.openclassrooms.realestatemanager.database.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openclassrooms.realestatemanager.model.Photo

class PhotoConverter {
    @TypeConverter
    fun toString(photos: List<Photo>): String {
        return Gson().toJson(photos)
    }

    @TypeConverter
    fun toPhoto(s: String): List<Photo> {
        val listPhotos = object : TypeToken<Array<Photo>>() {}.type
        return (Gson().fromJson(s, listPhotos) as Array<Photo>).toList()
    }
}