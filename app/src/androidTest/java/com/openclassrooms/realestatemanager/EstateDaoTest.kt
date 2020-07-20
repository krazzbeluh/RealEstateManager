package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.utils.LiveDataTestUtil
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class EstateDaoTest {
    private lateinit var database: RealEstateManagerDatabase

    private val addressForTests = Address(12, "rue de la paix", "Paris", 75000, "France")
    private val estateForTests = Estate(0, addressForTests, Estate.EstateType.FLAT, 291000, 3, 40, "Little apartment in Paris", listOf(), listOf(Estate.POI.SCHOOL, Estate.POI.PARK), "Paul Leclerc", false)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
                RealEstateManagerDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @Test
    fun insertAndGetEstate() {
        database.estateDao().insertEstate(estateForTests)

        val estates = LiveDataTestUtil.getValue(database.estateDao().getEstates())
        assertEquals(estates.first(), estateForTests)
    }

    @After
    @Throws(java.lang.Exception::class)
    fun closeDb() {
        database.close()
    }
}