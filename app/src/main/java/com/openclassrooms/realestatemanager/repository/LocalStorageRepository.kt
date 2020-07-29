package com.openclassrooms.realestatemanager.repository

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log


class LocalStorageRepository {
    companion object {
        private val TAG = LocalStorageRepository::class.java.simpleName
    }

    fun moveImageToAppDir(uri: Uri) {
        val image = BitmapFactory.decodeFile(uri.path)
        Log.d(TAG, "moveImageToAppDir: ${image.height} ${image.width}")
    }
}