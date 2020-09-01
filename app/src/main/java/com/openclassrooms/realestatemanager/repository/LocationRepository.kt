package com.openclassrooms.realestatemanager.repository

import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.network.LocationClient
import com.openclassrooms.realestatemanager.network.response.LocationResponse

open class LocationRepository(private val service: LocationClient) {
    suspend fun checkAddress(address: Address): LocationResponse {
        return service.getLocation(address)
    }

    suspend fun getLocation(city: String) = service.getLocation(city)
}