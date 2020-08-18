package com.openclassrooms.realestatemanager.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class LoanTestCase {
    @Test
    fun testLoanSimulatorConformsOtherSimulator() {
        val loan = Loan(150000, 10, 2.0, 15000)
        loan.apply {
            assertEquals(135000, borrowedCapital)
            assertEquals(1242.18, monthlyPayment)
            assertEquals(149061.6, rendering)
            assertEquals(14061.6, interests)
        }
    }
}