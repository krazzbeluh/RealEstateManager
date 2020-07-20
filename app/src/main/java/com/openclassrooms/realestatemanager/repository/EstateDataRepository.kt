package com.openclassrooms.realestatemanager.repository

import android.util.Log
import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.model.Estate

class EstateDataRepository(private val estateDao: EstateDao) {
    fun getEstates() = estateDao.getEstates()
    fun addEstate(estate: Estate) {
        Log.d("TAG", "addEstate: " + estateDao.insertEstate(estate))
        Log.d("TAG", "addEstate: " + getEstates().value)
    }
}