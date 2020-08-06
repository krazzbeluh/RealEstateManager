package com.openclassrooms.realestatemanager.model

import com.openclassrooms.realestatemanager.utils.round
import kotlin.math.pow

data class Loan(val amount: Int, val duration: Int, val rate: Double, val contribution: Int) {
    val borrowedCapital get() = amount - contribution
    val monthlyPayment get() = (borrowedCapital.toDouble() * rate / 100 / 12 / (1 - (1 + rate / 100 / 12).pow(0 - duration.toDouble() * 12))).round(2)
    val rendering get() = monthlyPayment * duration * 12
    val interests get() = (rendering - borrowedCapital).round(2)
}