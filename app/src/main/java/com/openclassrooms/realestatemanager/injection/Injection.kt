package com.openclassrooms.realestatemanager.injection

import android.app.Application
import android.content.Context
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Injection {
    companion object {
        fun provideViewModelFactory(application: Application): ViewModelFactory {
            val context = application.applicationContext
            val estateDataSource = provideEstateDataSource(context)
            val executor = provideExecutor()
            return ViewModelFactory.getInstance(application, estateDataSource, executor)
        }

        private fun provideEstateDataSource(context: Context) = EstateDataRepository(RealEstateManagerDatabase.getInstance(context).estateDao())
        private fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()
    }
}