package com.openclassrooms.realestatemanager.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.model.estate.SimpleEstate
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

    private val estate = Estate(SimpleEstate(
            0,
            Address(
                    12,
                    "rue des tests",
                    "Android",
                    75903,
                    "Google",
                    null,
                    null
            ),
            Estate.EstateType.FLAT,
            12,
            2,
            12,
            "Description",
            "Paul Leclerc",
            true
    ))
    private val estateList = listOf(estate)
    private val estateDao = mock<EstateDao> {
        on { getEstates() } doReturn MutableLiveData(estateList)
        on { insertEstate(estate) } doReturn 1234567890L
        on { updateEstate(estate) } doReturn 1234567890
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