package com.openclassrooms.realestatemanager.injection

import android.content.Context
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Injection {
    companion object {
        fun provideViewModelFactory(context: Context): ViewModelFactory {
            val estateDataSource = provideEstateDataSource(context)
            val executor = provideExecutor()
            return ViewModelFactory(estateDataSource, executor)
        }

        fun provideEstateDataSource(context: Context) = EstateDataRepository(RealEstateManagerDatabase.getInstance(context).estateDao())
        fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()
    }
}