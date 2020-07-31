package com.openclassrooms.realestatemanager.utils

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R

fun AppCompatActivity.showAlert(title: String, message: String) {
    AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
}