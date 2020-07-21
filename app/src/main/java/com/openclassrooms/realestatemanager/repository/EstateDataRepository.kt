package com.openclassrooms.realestatemanager.repository

import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.model.Estate

class EstateDataRepository(private val estateDao: EstateDao) {
    fun getEstates() = estateDao.getEstates()
    fun addEstate(estate: Estate) = estateDao.insertEstate(estate)
    fun updateEstate(estate: Estate) = estateDao.updateEstate(estate)
}