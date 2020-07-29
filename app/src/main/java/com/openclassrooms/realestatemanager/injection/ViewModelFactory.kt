package com.openclassrooms.realestatemanager.injection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.repository.LocalStorageRepository
import com.openclassrooms.realestatemanager.repository.LocationRepository
import com.openclassrooms.realestatemanager.ui.add.AddEstateViewModel
import com.openclassrooms.realestatemanager.ui.dialog.photo.PhotoDialogViewModel
import com.openclassrooms.realestatemanager.ui.main.MainViewModel
import java.util.concurrent.Executor

class ViewModelFactory private constructor(private val application: Application, private val estateDataRepository: EstateDataRepository, private val locationRepository: LocationRepository, private val localStorageRepository: LocalStorageRepository, private val executor: Executor) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            MainViewModel::class.java.isAssignableFrom(modelClass) -> {
                try {
                    modelClass.getConstructor(
                            EstateDataRepository::class.java, LocationRepository::class.java, Executor::class.java, Application::class.java
                    ).newInstance(estateDataRepository, locationRepository, executor, application)
                } catch (e: Exception) {
                    throw RuntimeException("Cannot create an instance of $modelClass", e)
                }
            }
            AddEstateViewModel::class.java.isAssignableFrom(modelClass) -> {
                try {
                    modelClass.getConstructor(
                            EstateDataRepository::class.java, Executor::class.java, Application::class.java
                    ).newInstance(estateDataRepository, executor, application)
                } catch (e: Exception) {
                    throw RuntimeException("Cannot create an instance of $modelClass", e)
                }
            }
            PhotoDialogViewModel::class.java.isAssignableFrom(modelClass) -> {
                try {
                    modelClass.getConstructor(
                            LocalStorageRepository::class.java, Application::class.java
                    ).newInstance(localStorageRepository, application)
                } catch (e: Exception) {
                    throw RuntimeException("Cannot create an instance of $modelClass", e)
                }
            }
            else -> super.create(modelClass)
        }
    }

    companion object {
        private var instance: ViewModelFactory? = null
        fun getInstance(
                application: Application,
                estateDataRepository: EstateDataRepository,
                locationRepository: LocationRepository,
                localStorageRepository: LocalStorageRepository,
                executor: Executor
        ): ViewModelFactory {
            if (instance == null) {
                instance = ViewModelFactory(application, estateDataRepository, locationRepository, localStorageRepository, executor)
            }
            return instance as ViewModelFactory
        }
    }
}