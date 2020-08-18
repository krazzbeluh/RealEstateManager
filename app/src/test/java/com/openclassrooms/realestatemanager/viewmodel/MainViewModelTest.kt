package com.openclassrooms.realestatemanager.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.repository.LocationRepository
import com.openclassrooms.realestatemanager.ui.main.MainViewModel
import com.openclassrooms.realestatemanager.utils.estate
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        val estateDataRepository = mock<EstateDataRepository> {
            on { getEstates() } doReturn MutableLiveData(listOf(estate))
        }
        val locationRepository = mock<LocationRepository>()
        val application = mock<Application>()
        viewModel = MainViewModel(estateDataRepository, locationRepository, application)
    }

    @Test
    fun testGetEstatesShouldReturnGivenValues() {
        assertEquals(listOf(estate), viewModel.getEstates().value)
    }
}