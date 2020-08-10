package com.openclassrooms.realestatemanager.injection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.repository.LocationRepository
import com.openclassrooms.realestatemanager.ui.add.AddEstateViewModel
import com.openclassrooms.realestatemanager.ui.loan.LoanViewModel
import com.openclassrooms.realestatemanager.ui.main.MainViewModel
import com.openclassrooms.realestatemanager.ui.map.MapViewModel
import com.openclassrooms.realestatemanager.ui.search.AdvancedSearchViewModel
import java.util.concurrent.Executor

class ViewModelFactory private constructor(
        private val application: Application,
        private val estateDataRepository: EstateDataRepository,
        private val locationRepository: LocationRepository,
        private val executor: Executor
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            when {
                MainViewModel::class.java.isAssignableFrom(modelClass) -> {
                    modelClass.getConstructor(
                            EstateDataRepository::class.java,
                            LocationRepository::class.java,
                            Executor::class.java,
                            Application::class.java
                    ).newInstance(estateDataRepository, locationRepository, executor, application)
                }
                AddEstateViewModel::class.java.isAssignableFrom(modelClass) -> {
                    modelClass.getConstructor(
                            EstateDataRepository::class.java,
                            Executor::class.java,
                            Application::class.java
                    ).newInstance(estateDataRepository, executor, application)
                }
                MapViewModel::class.java.isAssignableFrom(modelClass) -> {
                    modelClass.getConstructor(
                            EstateDataRepository::class.java,
                            Application::class.java
                    ).newInstance(estateDataRepository, application)
                }
                AdvancedSearchViewModel::class.java.isAssignableFrom(modelClass) -> {
                    modelClass.getConstructor(
                            EstateDataRepository::class.java,
                            LocationRepository::class.java,
                            Application::class.java
                    ).newInstance(estateDataRepository, locationRepository, application)
                }
                LoanViewModel::class.java.isAssignableFrom(modelClass) -> {
                    modelClass.getConstructor(Application::class.java)
                            .newInstance(application)
                }
                else -> super.create(modelClass)
            }
        } catch (e: Exception) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }

    companion object {
        private var instance: ViewModelFactory? = null
        fun getInstance(
                application: Application,
                estateDataRepository: EstateDataRepository,
                locationRepository: LocationRepository,
                executor: Executor
        ): ViewModelFactory {
            if (instance == null) {
                instance = ViewModelFactory(
                        application,
                        estateDataRepository,
                        locationRepository,
                        executor
                )
            }
            return instance as ViewModelFactory
        }
    }
}