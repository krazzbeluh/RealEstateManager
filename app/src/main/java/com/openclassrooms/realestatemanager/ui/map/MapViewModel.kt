package com.openclassrooms.realestatemanager.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.openclassrooms.realestatemanager.repository.EstateDataRepository

class MapViewModel(private val estateDataRepository: EstateDataRepository, application: Application) : AndroidViewModel(application) {
    fun getEstates() = estateDataRepository.getEstates()
}