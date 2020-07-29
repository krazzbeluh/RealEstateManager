package com.openclassrooms.realestatemanager.ui.dialog.photo

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.repository.LocalStorageRepository

class PhotoDialogViewModel(private val localStorageRepository: LocalStorageRepository, application: Application) : AndroidViewModel(application) {
    private val image = MutableLiveData<Bitmap>()
    fun getImage() = image as LiveData<Bitmap>

    fun getImageFromUri(uri: Uri) {
        localStorageRepository.moveImageToAppDir(uri)
    }
}