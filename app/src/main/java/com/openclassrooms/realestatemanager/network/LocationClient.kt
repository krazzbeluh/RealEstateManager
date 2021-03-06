package com.openclassrooms.realestatemanager.network

import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.network.response.LocationResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class LocationClient {
    companion object {
        @Suppress("unused")
        private val TAG = LocationClient::class.java.simpleName
        private const val BASE_URL = "https://nominatim.openstreetmap.org"
    }

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private val service = retrofit.create(LocationDao::class.java)

    suspend fun getLocation(address: Address): LocationResponse {
        return service.getLocationWithAddress("${address.number} ${address.route}", address.city, address.country, address.postCode.toString())
    }

    suspend fun getLocation(city: String) = service.getLocationWithCityName(city)
}