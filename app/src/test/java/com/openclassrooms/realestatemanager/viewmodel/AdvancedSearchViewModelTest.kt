package com.openclassrooms.realestatemanager.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.repository.LocationRepository
import com.openclassrooms.realestatemanager.ui.search.AdvancedSearchViewModel
import com.openclassrooms.realestatemanager.ui.search.EstateOrderField
import com.openclassrooms.realestatemanager.utils.estate
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AdvancedSearchViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AdvancedSearchViewModel

    @Before
    fun setUp() {
        val estateDataRepository = mock<EstateDataRepository>()
        val locationRepository = mock<LocationRepository>()
        val application = mock<Application>()
        viewModel = AdvancedSearchViewModel(estateDataRepository, locationRepository, application)
    }

    @Test
    fun testSearchShouldFilterList() {
        runBlocking {
            assertEquals(listOf(estate), viewModel.search(listOf(estate), 0..100, 0..100, 0..100, null, null, EstateOrderField.ROOMS, true))
            assertTrue(viewModel.search(listOf(estate), 0..1, 0..1, 0..1, null, null, EstateOrderField.ROOMS, false).isEmpty())
        }
    }
}