package com.openclassrooms.realestatemanager.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.repository.LocationRepository
import java.util.concurrent.Executor

class MainViewModel(private val estateDataRepository: EstateDataRepository, private val locationRepository: LocationRepository, private val executor: Executor, application: Application) : AndroidViewModel(application) {
    companion object {
        val TAG = MainViewModel::class.java.simpleName
    }

    fun getEstates(): LiveData<List<Estate>> {
        return estateDataRepository.getEstates()
    }

    suspend fun checkAddresses(estates: List<Estate>) {
        estates.forEach { estate ->
            val locationResponse = locationRepository.checkAddress(estate.address)
            if (!locationResponse.isValid()) return@forEach
            val location: LatLng
            try {
                location = locationResponse.getLatLnt()
                Log.d(TAG, "checkAddresses: $location")
            } catch (e: NumberFormatException) {
                Log.e(TAG, "checkAddresses: ", e)
                return@forEach
            }
            estate.address.setLocation(location)
            estateDataRepository.updateEstate(estate)
        }
    }
}