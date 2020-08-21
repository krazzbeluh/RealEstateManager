package com.openclassrooms.realestatemanager.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.utils.estate
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EstateDataRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var estateDataRepository: EstateDataRepository

    @Before
    fun setUp() {
        estateDataRepository = EstateDataRepository(estateDao)
    }

    private val estateList = listOf(estate)
    private val estateDao = mockk<EstateDao> {
        every { getEstates() } returns MutableLiveData(estateList)
        every { insertEstate(estate) } returns 1234567890L
        every { updateEstate(estate) } returns 1234567890
    }

    @Test
    fun testGetEstatesShouldCallDaoGetEstates() {
        assertEquals(estateList, estateDataRepository.getEstates().value)
    }

    @Test
    fun testAddEstateShouldCallInsertEstateFromDao() {
        assertEquals(1234567890L, estateDataRepository.addEstate(estate))
    }

    @Test
    fun testUpdateEstateShouldCallUpdateEstateFromDao() {
        assertEquals(1234567890, estateDataRepository.updateEstate(estate))
    }
}