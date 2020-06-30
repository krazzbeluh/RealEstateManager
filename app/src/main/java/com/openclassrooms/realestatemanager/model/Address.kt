package com.openclassrooms.realestatemanager.model

import java.io.Serializable

data class Address(val number: Int, val route: String, val city: String, val postCode: Int, val country: String): Serializable {
}