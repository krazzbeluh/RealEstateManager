package com.openclassrooms.realestatemanager.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import com.openclassrooms.realestatemanager.ui.add.AddEstateViewModel
import java.util.concurrent.Executor

class ViewModelFactory(private val estateDataRepository: EstateDataRepository, private val executor: Executor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEstateViewModel::class.java)) return AddEstateViewModel(estateDataRepository, executor) as T
        else throw IllegalArgumentException("Unknown ViewModel class")
    }
}