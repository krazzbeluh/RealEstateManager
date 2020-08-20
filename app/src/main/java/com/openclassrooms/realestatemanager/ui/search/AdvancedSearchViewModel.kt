package com.openclassrooms.realestatemanager.ui.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.repository.LocationRepository
import com.openclassrooms.realestatemanager.utils.NetworkUtils
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.distanceWith

// TODO: 18/08/2020 test
class AdvancedSearchViewModel(private val estateDataRepository: EstateDataRepository, private val locationRepository: LocationRepository, application: Application) : AndroidViewModel(application) {
    companion object {
        private val TAG = AdvancedSearchViewModel::class.java.simpleName
    }

    fun getEstates() = estateDataRepository.getEstates()

    suspend fun search(estates: List<Estate>, priceRange: IntRange, surfaceRange: IntRange, roomsRange: IntRange, around: String?, distance: Int?, orderBy: EstateOrderField, ascending: Boolean): MutableList<Estate> {
        var aroundLocation: LatLng? = null

        var matchingEstates = estates.toSet()
        matchingEstates = getEstatesWithMatchingPrice(matchingEstates, priceRange)
        matchingEstates = getEstatesWithMatchingSurface(matchingEstates, surfaceRange)
        matchingEstates = getEstatesWithMatchingRooms(matchingEstates, roomsRange)

        if (around != null && distance != null && Utils.isInternetAvailable(NetworkUtils(), getApplication<Application>().applicationContext)) {
            try {
                aroundLocation = locationRepository.getLocation(around).getLatLnt()
                matchingEstates = getEstatesWithMatchingLocation(matchingEstates, aroundLocation, distance * 1000)
            } catch (e: Exception) {
                Log.e(TAG, "search: ", e)
            }
        }

        val finalEstates = matchingEstates.toMutableList()
        if (ascending) finalEstates.sortBy {
            selectOrderByField(orderBy, aroundLocation, it)
        } else finalEstates.sortByDescending {
            selectOrderByField(orderBy, aroundLocation, it = it)
        }
        return finalEstates
    }

    private fun selectOrderByField(orderBy: EstateOrderField, baseLocation: LatLng?, it: Estate) = when (orderBy) {
        EstateOrderField.PRICE -> it.estate.price
        EstateOrderField.ROOMS -> it.estate.rooms
        EstateOrderField.SURFACE -> it.estate.area
        EstateOrderField.DISTANCE -> {
            val location = it.estate.address.getLocation()
            if (location != null && baseLocation != null) {
                baseLocation.distanceWith(location)
            } else {
                it.estate.price
            }
        }
    }

    private fun getEstatesWithMatchingPrice(estates: Set<Estate>, priceRange: IntRange): Set<Estate> {
        val matchingEstates = mutableSetOf<Estate>()
        for (estate in estates) if (estate.estate.price in priceRange) {
            matchingEstates.add(estate)
        }
        return matchingEstates
    }

    private fun getEstatesWithMatchingSurface(estates: Set<Estate>, surfaceRange: IntRange): Set<Estate> {
        val matchingEstates = mutableSetOf<Estate>()
        for (estate in estates) if (estate.estate.area in surfaceRange) {
            matchingEstates.add(estate)
        }
        return matchingEstates
    }

    private fun getEstatesWithMatchingRooms(estates: Set<Estate>, roomsRange: IntRange): Set<Estate> {
        val matchingEstates = mutableSetOf<Estate>()
        for (estate in estates) if (estate.estate.rooms in roomsRange) {
            matchingEstates.add(estate)
        }
        return matchingEstates
    }

    private fun getEstatesWithMatchingLocation(estates: Set<Estate>, baseLocation: LatLng, maxDistance: Int): Set<Estate> {
        val matchingEstates = mutableSetOf<Estate>()
        for (estate in estates) {
            val location = estate.estate.address.getLocation()
            if (location != null && baseLocation.distanceWith(location) < maxDistance) matchingEstates.add(estate)
        }
        return matchingEstates
    }
}