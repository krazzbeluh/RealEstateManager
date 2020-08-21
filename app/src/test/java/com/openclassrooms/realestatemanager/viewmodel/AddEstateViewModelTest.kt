package com.openclassrooms.realestatemanager.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.model.estate.AssociatedPOI
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.ui.add.AddEstateViewModel
import com.openclassrooms.realestatemanager.utils.estate
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
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
    private var application = mockk<Application> { }
    private lateinit var viewModel: AddEstateViewModel

    private val estateDao = mockk<EstateDao>()

    private val estateDataRepository: EstateDataRepository = spyk(EstateDataRepository(estateDao)) {
        every { addEstate(any()) } returns 0L
    }

    @Before
    fun setUp() {
        viewModel = AddEstateViewModel(estateDataRepository, executor, application)
    }

    @Test
    fun testAddEstateShouldNotDoAnyThingIfADataIsNull() {
        viewModel.addEstate(null, null, null, null, null, "", listOf(), "", listOf(), true, null)
        verify(exactly = 0) { estateDataRepository.getEstates() }
        verify(exactly = 0) { estateDataRepository.addEstate(estate) }
        verify(exactly = 0) { estateDataRepository.updateEstate(estate) }
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
        verify(exactly = 0) { estateDataRepository.getEstates() }
        verify(exactly = 1) { estateDataRepository.addEstate(estate) }
        verify(exactly = 0) { estateDataRepository.updateEstate(estate) }
    }
}