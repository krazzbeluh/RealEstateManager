package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.provider.EstateContentProvider
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Before
import org.junit.Test


class ItemContentProviderTest {
    // FOR DATA
    private lateinit var context: Context
    private var contentResolver: ContentResolver? = null

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        contentResolver = context.contentResolver
    }

    @Test
    fun insertAndGetEstate() {
        // BEFORE : Adding demo item
        val userUri: Uri? = contentResolver!!.insert(EstateContentProvider.URI_ITEM, generateItem())
        // TEST
        val cursor: Cursor? = contentResolver?.query(ContentUris.withAppendedId(EstateContentProvider.URI_ITEM, USER_ID), null, null, null, null)
        assertThat(cursor, notNullValue())
        assertEquals(cursor?.moveToLast(), true)
        assertEquals(cursor?.getString(cursor.getColumnIndexOrThrow("description")), "Petit appartement dans Paris")
    }

    // ---
    private fun generateItem(): ContentValues? = ContentValues().apply {
        put("addressNumber", 23)
        put("addressRoute", "rue de la république")
        put("addressCity", "Paris")
        put("addressPostCode", 75000)
        put("addressCountry", "France")
        put("estateType", "HOUSE")
        put("estatePrice", 374926)
        put("estateRooms", 4)
        put("estateArea", 54)
        put("estateDescription", "Petit appartement dans Paris")
        put("estateAgent", "Paul Leclerc")
        put("isEstateSold", false)
    }

    companion object {
        // DATA SET FOR TEST
        private val USER_ID: Long = 1
    }
}