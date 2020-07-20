package com.openclassrooms.realestatemanager.ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import java.util.concurrent.Executor

class AddEstateViewModel(private val estateDataRepository: EstateDataRepository, private val executor: Executor, application: Application) : AndroidViewModel(application) {
    fun addEstate(address: Address?, type: Estate.EstateType?, price: Int?, rooms: Int?, area: Int?, description: String, photos: List<Photo>, agent: String, pois: List<Estate.POI>, isSold: Boolean) {
        if (address != null && type is Estate.EstateType && price != null && rooms != null && area != null && description.isNotBlank() /* TODO && photos.isNotEmpty */ && agent.isNotBlank()) {
            executor.execute { estateDataRepository.addEstate(Estate(0, address, type, price, rooms, area, description, photos, pois, agent, isSold)) }
        }
    }
}