package com.openclassrooms.realestatemanager.injection

import android.app.Application
import android.content.Context
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.network.LocationClient
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.repository.LocalStorageRepository
import com.openclassrooms.realestatemanager.repository.LocationRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Injection {
    companion object {
        fun provideViewModelFactory(application: Application): ViewModelFactory {
            val context = application.applicationContext
            val estateDataSource = provideEstateDataSource(context)
            val locationRepository = provideLocationRepository()
            val localStorageRepository = provideLocalStorageRepository()
            val executor = provideExecutor()
            return ViewModelFactory.getInstance(application, estateDataSource, locationRepository, localStorageRepository, executor)
        }

        private fun provideEstateDataSource(context: Context) = EstateDataRepository(RealEstateManagerDatabase.getInstance(context).estateDao())
        private fun provideLocationRepository() = LocationRepository(LocationClient())
        private fun provideLocalStorageRepository() = LocalStorageRepository()
        private fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()
    }
}