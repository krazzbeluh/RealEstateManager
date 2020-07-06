package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.realestatemanager.model.Estate

@Dao
interface EstateDao {
    @Query("select * from Estate")
    fun getEstates(): LiveData<List<Estate>>

    @Insert
    fun insertEstate(estate: Estate): Long

    @Query("delete from Estate where id = :estateId")
    fun deleteEstate(estateId: Long): Int
}