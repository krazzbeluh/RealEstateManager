package com.openclassrooms.realestatemanager.model

import android.net.Uri
import java.io.Serializable

// TODO: set photo field not nullable
data class Photo(val photo: Uri?, val description: String) : Serializable