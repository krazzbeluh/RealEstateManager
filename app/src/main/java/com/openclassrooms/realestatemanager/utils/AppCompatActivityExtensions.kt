package com.openclassrooms.realestatemanager.utils

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R

fun AppCompatActivity.showAlert(title: String, message: String, listener: DialogInterface.OnClickListener? = null) {
    AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), listener)
            .show()
}