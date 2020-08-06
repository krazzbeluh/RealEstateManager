package com.openclassrooms.realestatemanager.ui.loan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.model.Loan

class LoanViewModel(application: Application) : AndroidViewModel(application) {
    val loan = MutableLiveData<Loan?>()
    val isAlertShown = MutableLiveData<Boolean>(false)

    fun getLoan() = loan as LiveData<Loan?>

    fun getIsAlertShown() = isAlertShown as LiveData<Boolean>

    fun simulate(amount: String, duration: String, rate: String, contribution: String) {
        val amount1 = amount.toIntOrNull()
        val duration1 = duration.toIntOrNull()
        val rate1 = rate.toDoubleOrNull()
        val contribution1 = contribution.toIntOrNull()
        if (amount1 != null && duration1 != null && rate1 != null && contribution1 != null)
            loan.value = Loan(amount1, duration1, rate1, contribution1)
        else isAlertShown.value = true
    }

    fun hideAlert() {
        isAlertShown.value = false
    }
}