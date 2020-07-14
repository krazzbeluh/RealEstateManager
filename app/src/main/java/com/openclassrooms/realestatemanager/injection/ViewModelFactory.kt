package com.openclassrooms.realestatemanager.injection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.repository.EstateDataRepository
import java.util.concurrent.Executor

class ViewModelFactory private constructor(private val application: Application, private val estateDataRepository: EstateDataRepository, private val executor: Executor) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (AndroidViewModel::class.java.isAssignableFrom(modelClass)) {
            try {
                modelClass.getConstructor(
                        EstateDataRepository::class.java, Executor::class.java, Application::class.java
                ).newInstance(estateDataRepository, executor, application)
            } catch (e: Exception) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        } else super.create(modelClass)
    }

    companion object {
        private var instance: ViewModelFactory? = null
        fun getInstance(
                application: Application,
                estateDataRepository: EstateDataRepository,
                executor: Executor
        ): ViewModelFactory {
            if (instance == null) {
                instance = ViewModelFactory(application, estateDataRepository, executor)
            }
            return instance as ViewModelFactory
        }
    }
}