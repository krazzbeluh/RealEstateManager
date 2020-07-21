package com.openclassrooms.realestatemanager.network

import com.openclassrooms.realestatemanager.network.response.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationDao {
    @GET("search?format=geocodejson")
    suspend fun getLocationWithAddress(@Query("street") street: String, @Query("city") city: String, @Query("country") country: String, @Query("postalCode") postalCode: String): LocationResponse
}