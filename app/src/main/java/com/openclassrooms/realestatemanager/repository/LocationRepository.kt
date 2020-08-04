package com.openclassrooms.realestatemanager.repository

import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.network.LocationClient

class LocationRepository(private val service: LocationClient) {
    suspend fun checkAddress(address: Address) = service.getLocation(address)
    suspend fun getLocation(city: String) = service.getLocation(city)
}