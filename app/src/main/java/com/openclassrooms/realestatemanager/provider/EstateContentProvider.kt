package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.model.estate.Estate


class EstateContentProvider : ContentProvider() {
    override fun onCreate() = true
    override fun insert(uri: Uri, values: ContentValues?): Nothing? = null
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?) = 0
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = 0

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val exception = IllegalArgumentException("Failed to query uri $uri")
        RealEstateManagerDatabase.getInstance(context
                ?: throw exception).estateDao().getEstatesWithCursor().apply {
            setNotificationUri(context?.contentResolver, uri)
            return this
        }
    }

    override fun getType(uri: Uri): String? = "vnd.android.cursor.item/$AUTHORITY.${Estate::class.java.simpleName}"

    companion object {
        const val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
        private val TABLE_NAME = Estate::class.java.simpleName
        val URI_ITEM = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }
}