package com.openclassrooms.realestatemanager.model

import android.graphics.Bitmap
import java.io.Serializable

// TODO: set photo field not nullable
data class Photo(val photo: Bitmap?, val description: String): Serializable {

}