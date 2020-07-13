package com.openclassrooms.realestatemanager.database.typeconverter

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.model.Estate

class EstateTypeConverter {
    @TypeConverter
    fun toString(type: Estate.EstateType): String {
        return type.toString()
    }

    @TypeConverter
    fun toEstateType(s: String): Estate.EstateType {
        return Estate.EstateType.valueOf(s)
    }
}