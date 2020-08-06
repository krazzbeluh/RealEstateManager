package com.openclassrooms.realestatemanager.ui

import android.content.Context
import android.content.SharedPreferences
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.openclassrooms.realestatemanager.R

interface ConvertibleActivity {
    companion object {
        const val IS_DOLLAR = "IS_DOLLAR_UNIT"
    }

    var isDollar: Boolean
        get() = preferences.getBoolean(IS_DOLLAR, true)
        set(value) = preferences.edit().putBoolean(IS_DOLLAR, value).apply()
    val context: Context
    var preferences: SharedPreferences

    fun didTapConvert(item: MenuItem) {
        isDollar = !isDollar
        item.icon = ContextCompat.getDrawable(context, if (isDollar) R.drawable.euro else R.drawable.dollar)
    }
}