package com.openclassrooms.realestatemanager.ui.loan

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment.Companion.ARG_ESTATE

class LoanActivity : AppCompatActivity() {
    companion object {
        val TAG = LoanActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan)

        val estate = intent.getSerializableExtra(ARG_ESTATE) as? Estate
        Log.d(TAG, "onCreate: $estate")
    }
}