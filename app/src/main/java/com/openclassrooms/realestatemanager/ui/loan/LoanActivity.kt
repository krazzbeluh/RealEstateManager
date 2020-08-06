package com.openclassrooms.realestatemanager.ui.loan

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment.Companion.ARG_ESTATE
import com.openclassrooms.realestatemanager.utils.showAlert

class LoanActivity : AppCompatActivity() {
    companion object {
        val TAG = LoanActivity::class.java.simpleName
    }

    private lateinit var viewModel: LoanViewModel

    private lateinit var amountEditText: EditText
    private lateinit var durationEditText: EditText
    private lateinit var rateEditText: EditText
    private lateinit var contributionEditText: EditText
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan)

        val viewModelFactory = Injection.provideViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoanViewModel::class.java)
        observeValues()

        val estate = intent.getSerializableExtra(ARG_ESTATE) as? Estate

        amountEditText = findViewById(R.id.loan_amount)
        durationEditText = findViewById(R.id.loan_duration)
        rateEditText = findViewById(R.id.loan_rate)
        contributionEditText = findViewById<EditText>(R.id.loan_contribution).apply { setText(0.toString()) }
        resultTextView = findViewById(R.id.loan_result)

        estate?.let { amountEditText.setText(it.estate.price.toString()) }
    }

    fun onClickOnCalculateButton(@Suppress("UNUSED_PARAMETER") v: View) {
        viewModel.simulate(
                amountEditText.text.toString(),
                durationEditText.text.toString(),
                rateEditText.text.toString(),
                contributionEditText.text.toString()
        )
    }

    private fun observeValues() {
        viewModel.apply {
            getLoan().observe(this@LoanActivity, Observer {
                if (it != null) {
                    resultTextView.text = getString(
                            R.string.loan_result_text,
                            it.borrowedCapital.toString(),
                            it.monthlyPayment.toString(),
                            it.rendering.toString(),
                            it.interests.toString()
                    )
                }
            })

            getIsAlertShown().observe(this@LoanActivity, Observer {
                if (it) this@LoanActivity.showAlert(getString(R.string.error), getString(R.string.not_a_number_error), DialogInterface.OnClickListener { _, _ ->
                    this.hideAlert()
                })
            })
        }
    }
}