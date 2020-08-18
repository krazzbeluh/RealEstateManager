package com.openclassrooms.realestatemanager.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.openclassrooms.realestatemanager.ui.loan.LoanViewModel
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoanViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val application = mock<Application>()
    private lateinit var viewModel: LoanViewModel

    @Before
    fun setUp() {
        viewModel = LoanViewModel(application)
    }

    @Test
    fun testSimulateShouldSetLoanValueIfNoError() {
        assertNull(viewModel.getLoan().value)
        viewModel.simulate("150000", "15", "2", "15000")
        assertNotNull(viewModel.getLoan().value)
    }

    @Test
    fun testSimulateShouldShowAlertIfUnConvertibleValueAndHideAlertShouldSetValueToFalse() {
        assertFalse(viewModel.getIsAlertShown().value!!)
        viewModel.simulate("", "", "", "")
        assertTrue(viewModel.getIsAlertShown().value!!)
        viewModel.hideAlert()
        assertFalse(viewModel.getIsAlertShown().value!!)
    }
}