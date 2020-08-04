package com.openclassrooms.realestatemanager.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.repository.EstateDataRepository

class AdvancedSearchViewModel(private val estateDataRepository: EstateDataRepository, application: Application) : AndroidViewModel(application) {
    companion object {
        private val TAG = AdvancedSearchViewModel::class.java.simpleName
    }

    fun getEstates() = estateDataRepository.getEstates()

    fun search(estates: List<Estate>, priceRange: IntRange, surfaceRange: IntRange, roomsRange: IntRange, orderBy: EstateOrderField, ascending: Boolean): MutableList<Estate> {
        var matchingEstates = estates.toSet()
        matchingEstates = getEstatesWithMatchingPrice(matchingEstates, priceRange)
        matchingEstates = getEstatesWithMatchingSurface(matchingEstates, surfaceRange)
        matchingEstates = getEstatesWithMatchingRooms(matchingEstates, roomsRange)

        val finalEstates = matchingEstates.toMutableList()
        if (ascending) {
            finalEstates.sortBy {
                selectOrderByField(orderBy, it)
            }
        } else {
            finalEstates.sortByDescending {
                selectOrderByField(orderBy, it = it)
            }
        }
        return finalEstates
    }

    private fun selectOrderByField(orderBy: EstateOrderField, it: Estate) = when (orderBy) {
        EstateOrderField.PRICE -> it.estate.price
        EstateOrderField.ROOMS -> it.estate.rooms
        EstateOrderField.SURFACE -> it.estate.area
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
}