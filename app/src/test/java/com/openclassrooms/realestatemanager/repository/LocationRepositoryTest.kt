package com.openclassrooms.realestatemanager.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openclassrooms.realestatemanager.network.LocationClient
import com.openclassrooms.realestatemanager.utils.address
import com.openclassrooms.realestatemanager.utils.locationResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocationRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var locationRepository: LocationRepository

    private lateinit var locationClient: LocationClient

    @Before
    fun setUp() {
        locationClient = mockk {
            coEvery {
                getLocation(address)
            } returns locationResponse
        }
        locationRepository = LocationRepository(locationClient)
    }

    @Test
    fun testCheckAddressShouldReturnValidValues() {
        runBlocking {
            assertEquals(locationResponse, locationRepository.checkAddress(address))
        }
    }
}