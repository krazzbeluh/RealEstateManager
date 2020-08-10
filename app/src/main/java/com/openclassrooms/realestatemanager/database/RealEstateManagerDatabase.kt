package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.database.typeconverter.EstateTypeConverter
import com.openclassrooms.realestatemanager.database.typeconverter.POIConverter
import com.openclassrooms.realestatemanager.database.typeconverter.UriConverter
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.estate.AssociatedPOI
import com.openclassrooms.realestatemanager.model.estate.SimpleEstate

@Database(entities = [SimpleEstate::class, AssociatedPOI::class, Photo::class], version = 1, exportSchema = false)
@TypeConverters(EstateTypeConverter::class, POIConverter::class, UriConverter::class)
abstract class RealEstateManagerDatabase : RoomDatabase() {
    abstract fun estateDao(): EstateDao

    companion object {
        @Volatile
        private var INSTANCE: RealEstateManagerDatabase? = null

        fun getInstance(context: Context): RealEstateManagerDatabase {
            if (INSTANCE == null) {
                synchronized(RealEstateManagerDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                RealEstateManagerDatabase::class.java, "RealEstateManager.db")
                                .addCallback(prepopulateDatabase())
                                .build()
                    }
                }
            }
            return INSTANCE as RealEstateManagerDatabase
        }

        private fun prepopulateDatabase(): Callback {
            return object : Callback() {
            }
        }
    }
}