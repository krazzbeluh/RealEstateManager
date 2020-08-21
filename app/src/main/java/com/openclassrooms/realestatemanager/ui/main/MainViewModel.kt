package com.openclassrooms.realestatemanager.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.repository.LocationRepository

class MainViewModel(private val estateDataRepository: EstateDataRepository, private val locationRepository: LocationRepository, application: Application) : AndroidViewModel(application) {
    companion object {
        val TAG = MainViewModel::class.java.simpleName
    }

    fun getEstates(): LiveData<List<Estate>> {
        return estateDataRepository.getEstates()
    }

    suspend fun checkAddresses(estates: List<Estate>) {
        estates.forEach { estate ->
            val locationResponse = locationRepository.checkAddress(estate.estate.address)
            if (!locationResponse.isValid()) return@forEach
            val location: LatLng
            try {
                location = locationResponse.getLatLnt()
            } catch (e: NumberFormatException) {
                return@forEach
            }
            estate.estate.address.setLocation(location)
            estateDataRepository.updateEstate(estate)
        }
    }
}