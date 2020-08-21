package com.openclassrooms.realestatemanager.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.ui.map.MapViewModel
import com.openclassrooms.realestatemanager.utils.estate
import io.mockk.every
import io.mockk.mockk
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
        val estateDataRepository = mockk<EstateDataRepository> {
            every { getEstates() } returns MutableLiveData(listOf(estate))
        }
        val application = mockk<Application>()
        viewModel = MapViewModel(estateDataRepository, application)
    }

    @Test
    fun testGetEstatesShouldReturnPassedData() {
        assertEquals(listOf(estate), viewModel.getEstates().value)
    }
}