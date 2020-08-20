package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.model.estate.SimpleEstate
import com.openclassrooms.realestatemanager.provider.EstateContentProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EstateContentProviderTest {
    private lateinit var contentResolver: ContentResolver
    private lateinit var database: RealEstateManagerDatabase

    private val address = Address(
            12,
            "rue des tests",
            "Android",
            75903,
            "Google",
            null,
            null
    )
    private val estate = Estate(SimpleEstate(
            0,
            address,
            Estate.EstateType.FLAT,
            12,
            2,
            12,
            "Description",
            "Paul Leclerc",
            true
    ))

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext, RealEstateManagerDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        contentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @Test
    fun testGetEstatesShouldContainData() {
        val cursor = contentResolver.query(EstateContentProvider.URI_ITEM, null, null, null, null)

        database.estateDao().insertEstate(estate)

        val newCursor = contentResolver.query(EstateContentProvider.URI_ITEM, null, null, null, null)
        assertNotNull(cursor)
        assertNotNull(newCursor)
        assertEquals(0, cursor?.count)
        assertEquals(1, newCursor?.count)
        assertTrue(newCursor?.moveToFirst() ?: false)
        val agent = newCursor?.getString(cursor?.getColumnIndexOrThrow("agent") ?: 0)
        assertEquals(estate.estate.agent, agent)
        cursor?.close()
    }
}