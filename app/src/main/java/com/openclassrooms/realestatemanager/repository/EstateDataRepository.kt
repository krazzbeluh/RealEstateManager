package com.openclassrooms.realestatemanager.repository

import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.model.estate.Estate

open class EstateDataRepository(private val estateDao: EstateDao) {
    open fun getEstates() = estateDao.getEstates()
    open fun addEstate(estate: Estate) = estateDao.insertEstate(estate)
    open fun updateEstate(estate: Estate) = estateDao.updateEstate(estate)
}