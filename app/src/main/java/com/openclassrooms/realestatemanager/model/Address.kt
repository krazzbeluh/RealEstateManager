package com.openclassrooms.realestatemanager.model

import java.io.Serializable

data class Address(val number: Int, val route: String, val city: String, val postCode: Int, val country: String) : Serializable {
    fun format(): String {
        return "$number $route" +
                "\n$postCode" +
                "\n$city" +
                "\n$country"
    }

    override fun toString(): String = "$number $route, $postCode $city, $country"
}