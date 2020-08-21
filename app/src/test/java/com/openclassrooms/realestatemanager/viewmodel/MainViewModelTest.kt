package com.openclassrooms.realestatemanager.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.model.estate.SimpleEstate
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.repository.LocationRepository
import com.openclassrooms.realestatemanager.ui.main.MainViewModel
import com.openclassrooms.realestatemanager.utils.estate
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @MockK
    lateinit var estateDataRepository: EstateDataRepository

    @MockK
    lateinit var locationRepository: LocationRepository

    @MockK
    lateinit var application: Application

    @Before
    fun setUp() {
        MockKAnnotations.init(this, true)
        every { estateDataRepository.getEstates() } returns MutableLiveData(listOf(estate))
        every { estateDataRepository.updateEstate(any()) } returns 0
        viewModel = MainViewModel(estateDataRepository, locationRepository, application)
    }

    @Test
    fun testGetEstatesShouldReturnGivenValues() {
        assertEquals(listOf(estate), viewModel.getEstates().value)
    }

    @Test
    fun testCheckAddressesShouldCallSetLocationIfNoError() {
        val address = mockk<Address> {
            every { setLocation(any()) } returns Unit
        }
        coEvery { locationRepository.checkAddress(any()) } returns mockk {
            every { isValid() } returns true
            every { getLatLnt() } returns LatLng(1.0, 1.0)
        }
        viewModel = MainViewModel(estateDataRepository, locationRepository, application)

        val nearbyPointsOfInterest = estate.nearbyPointsOfInterests
        val photos = estate.photos
        val newEstate = Estate(SimpleEstate(estate.estate.id, address, estate.estate.type, estate.estate.price, estate.estate.rooms, estate.estate.area, estate.estate.description, estate.estate.agent, estate.estate.sold)).apply {
            this.nearbyPointsOfInterests = nearbyPointsOfInterest
            this.photos = photos
        }
        runBlocking { viewModel.checkAddresses(listOf(newEstate)) }

        verify { address.setLocation(any()) }
    }
}