package com.openclassrooms.realestatemanager.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.ui.map.MapViewModel
import com.openclassrooms.realestatemanager.utils.estate
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MapViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MapViewModel

    @Before
    fun setUp() {
        val estateDataRepository = mock<EstateDataRepository> {
            on { getEstates() } doReturn MutableLiveData(listOf(estate))
        }
        val application = mock<Application>()
        viewModel = MapViewModel(estateDataRepository, application)
    }

    @Test
    fun testGetEstatesShouldReturnPassedData() {
        assertEquals(listOf(estate), viewModel.getEstates().value)
    }
}