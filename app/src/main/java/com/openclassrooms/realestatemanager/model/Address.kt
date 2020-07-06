package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.ForeignKey
import java.io.Serializable

@Entity(foreignKeys = [ForeignKey(entity = Estate::class, parentColumns = arrayOf("id"), childColumns = arrayOf("estateId"))])
data class Address(val estateId: Int, val number: Int, val route: String, val city: String, val postCode: Int, val country: String) : Serializable {
    fun toFormattedAddress(): String {
        return "$number $route" +
                "\n$city" +
                "\n$postCode" +
                "\n$country"
    }
}