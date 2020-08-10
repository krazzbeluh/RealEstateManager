package com.openclassrooms.realestatemanager.ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.estate.AssociatedPOI
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.model.estate.SimpleEstate
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import java.util.concurrent.Executor

class AddEstateViewModel(private val estateDataRepository: EstateDataRepository, private val executor: Executor, application: Application) : AndroidViewModel(application) {
    fun addEstate(address: Address?, type: Estate.EstateType?, price: Int?, rooms: Int?, area: Int?, description: String, photos: List<Photo>, agent: String, pois: List<AssociatedPOI.POI>, isSold: Boolean) {
        if (address != null && type is Estate.EstateType && price != null && rooms != null && area != null && description.isNotBlank() && photos.isNotEmpty() && agent.isNotBlank()) {
            val estate = Estate(SimpleEstate(0, address, type, price, rooms, area, description, agent, isSold))
            pois.forEach { estate.nearbyPointsOfInterests.add(AssociatedPOI(0, 0, it)) }
            estate.photos = photos
            executor.execute { estateDataRepository.addEstate(estate) }
        }
    }
}