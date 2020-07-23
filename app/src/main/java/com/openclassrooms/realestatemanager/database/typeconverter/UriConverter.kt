package com.openclassrooms.realestatemanager.database.typeconverter

import android.net.Uri
import androidx.room.TypeConverter

class UriConverter {
    @TypeConverter
    fun toUri(s: String): Uri {
        return Uri.parse(s)
    }

    @TypeConverter
    fun toString(uri: Uri): String {
        return uri.toString()
    }
}