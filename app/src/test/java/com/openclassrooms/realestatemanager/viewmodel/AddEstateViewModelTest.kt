package com.openclassrooms.realestatemanager.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.model.estate.AssociatedPOI
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.ui.add.AddEstateViewModel
import com.openclassrooms.realestatemanager.utils.estate
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executor

class AddEstateViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val executor: Executor = Executor {
        it.run()
    }
    private var application = mock<Application> { }
    private lateinit var viewModel: AddEstateViewModel

    private val estateDao = mock<EstateDao>()

    private val estateDataRepository: EstateDataRepository = spy(EstateDataRepository(estateDao))

    @Before
    fun setUp() {
        viewModel = AddEstateViewModel(estateDataRepository, executor, application)
    }

    @Test
    fun testAddEstateShouldNotDoAnyThingIfADataIsNull() {
        viewModel.addEstate(null, null, null, null, null, "", listOf(), "", listOf(), true, null)
        verify(estateDataRepository, never()).getEstates()
        verify(estateDataRepository, never()).addEstate(estate)
        verify(estateDataRepository, never()).updateEstate(estate)
    }

    @Test
    fun testAddEstateShouldCallAddEstateIfNoNullData() {
        val pois = mutableListOf<AssociatedPOI.POI>()
        estate.nearbyPointsOfInterests.forEach { pois.add(it.poi) }
        viewModel.addEstate(
                estate.estate.address,
                estate.estate.type,
                estate.estate.price,
                estate.estate.rooms,
                estate.estate.area,
                estate.estate.description,
                estate.photos,
                estate.estate.agent,
                pois,
                estate.estate.sold,
                null
        )
        verify(estateDataRepository, never()).getEstates()
        verify(estateDataRepository).addEstate(estate)
        verify(estateDataRepository, never()).updateEstate(estate)
    }
}