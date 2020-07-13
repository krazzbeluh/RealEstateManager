package com.openclassrooms.realestatemanager.utils

import android.text.Editable

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)