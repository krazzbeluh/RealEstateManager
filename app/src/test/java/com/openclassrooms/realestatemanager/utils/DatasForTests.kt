package com.openclassrooms.realestatemanager.utils

import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.model.estate.SimpleEstate
import com.openclassrooms.realestatemanager.network.response.LocationResponse

val address = Address(
        12,
        "rue des tests",
        "Android",
        75903,
        "Google",
        null,
        null
)
val estate = Estate(SimpleEstate(
        0,
        address,
        Estate.EstateType.FLAT,
        12,
        2,
        12,
        "Description",
        "Paul Leclerc",
        true
))
val locationResponse = LocationResponse()