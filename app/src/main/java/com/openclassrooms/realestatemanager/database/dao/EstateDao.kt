package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.estate.AssociatedPOI
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.model.estate.SimpleEstate

@Dao
interface EstateDao {
    @Transaction
    @Query("select * from SimpleEstate")
    fun getEstates(): LiveData<List<Estate>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSimpleEstate(estate: SimpleEstate): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAssociatedPOI(poi: AssociatedPOI)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSimpleEstate(estate: SimpleEstate)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAssociatedPOI(poi: AssociatedPOI)

    @Delete
    fun deleteSimpleEstate(estate: SimpleEstate)

    @Transaction
    fun insertEstate(estate: Estate): Long {
        val id = insertSimpleEstate(estate.estate)
        estate.nearbyPointsOfInterests.forEach { poi ->
            poi.estateId = id
            insertAssociatedPOI(poi)
        }
        return id
    }

    @Transaction
    fun updateEstate(estate: Estate) {
        updateSimpleEstate(estate.estate)
        estate.nearbyPointsOfInterests.forEach { poi ->
            updateAssociatedPOI(poi)
        }
    }

    @Transaction
    fun deleteEstate(estate: Estate) {
        deleteSimpleEstate(estate.estate)
    }
}