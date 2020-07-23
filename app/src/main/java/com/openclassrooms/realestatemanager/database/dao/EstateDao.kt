package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.estate.AssociatedPOI
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.model.estate.SimpleEstate

@Dao
interface EstateDao {
    /**
     * SELECT
     */
    @Transaction
    @Query("select * from SimpleEstate")
    fun getEstates(): LiveData<List<Estate>>

    /**
     * INSERT
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSimpleEstate(estate: SimpleEstate): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAssociatedPOI(poi: AssociatedPOI)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPhoto(photo: Photo)

    /**
     * UPDATE
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSimpleEstate(estate: SimpleEstate)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAssociatedPOI(poi: AssociatedPOI)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePhoto(photo: Photo)

    /**
     * DELETE
     */
    @Delete
    fun deleteSimpleEstate(estate: SimpleEstate)

    @Transaction
    fun insertEstate(estate: Estate): Long {
        val id = insertSimpleEstate(estate.estate)
        estate.nearbyPointsOfInterests.forEach { poi ->
            poi.estateId = id
            insertAssociatedPOI(poi)
        }
        estate.photos.forEach { photo ->
            photo.estateId = id
            insertPhoto(photo)
        }
        return id
    }

    @Transaction
    fun updateEstate(estate: Estate) {
        updateSimpleEstate(estate.estate)
        estate.nearbyPointsOfInterests.forEach { updateAssociatedPOI(it) }
        estate.photos.forEach { updatePhoto(it) }
    }

    @Transaction
    fun deleteEstate(estate: Estate) {
        deleteSimpleEstate(estate.estate)
    }
}