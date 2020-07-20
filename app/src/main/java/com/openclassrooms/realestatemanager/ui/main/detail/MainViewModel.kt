package com.openclassrooms.realestatemanager.ui.main.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import java.util.concurrent.Executor

class MainViewModel(private val estateDataRepository: EstateDataRepository, private val executor: Executor, application: Application) : AndroidViewModel(application) {
    fun getEstates(): LiveData<List<Estate>> {
        return estateDataRepository.getEstates()
    }
}