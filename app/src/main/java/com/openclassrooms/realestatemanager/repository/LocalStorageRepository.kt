package com.openclassrooms.realestatemanager.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.FileUtils
import android.os.ParcelFileDescriptor
import androidx.annotation.RequiresApi
import java.io.*
import java.util.*


class LocalStorageRepository {
    @RequiresApi(Build.VERSION_CODES.Q)
    fun fetchImageFromStorageWithSdkQ(uri: Uri, parcelFileDescriptor: ParcelFileDescriptor, cacheDir: File): Bitmap? { //TODO: Ask Nicolas if it's possible to be sure to have an uri which refers an image
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val fileName: String = UUID.randomUUID().toString()
        val file = File(cacheDir, fileName)
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        if (outputStream != null) {
            try {
                FileUtils.copy(inputStream, outputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return BitmapFactory.decodeFile(file.path)
    }
}