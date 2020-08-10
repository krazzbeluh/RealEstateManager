package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.estate.AssociatedPOI
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.model.estate.SimpleEstate
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
    private val simpleEstate = SimpleEstate(0, addressForTests, Estate.EstateType.FLAT, 987654, 3, 234, "Little apartment in Paris", "Paul Leclerc", false)
    private val estateForTests = Estate(simpleEstate)

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
        assertEquals(estates?.first(), estateForTests)
    }

    @After
    @Throws(java.lang.Exception::class)
    fun closeDb() {
        database.close()
    }

    init {
        estateForTests.nearbyPointsOfInterests = mutableListOf(AssociatedPOI(0, 0, AssociatedPOI.POI.SCHOOL), AssociatedPOI(0, 0, AssociatedPOI.POI.PARK))
    }
}