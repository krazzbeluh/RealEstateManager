package com.openclassrooms.realestatemanager.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Address

class AddressDialogFragment(val completionHandler: (Address) -> Unit) : DialogFragment() {
    companion object {
        fun newInstance(title: String, completionHandler: (Address) -> Unit): AddressDialogFragment {
            val frag = AddressDialogFragment(completionHandler)
            val args = Bundle()
            args.putString("title", title)
            frag.arguments = args
            return frag
        }
    }

    private lateinit var numberEditText: EditText
    private lateinit var routeEditText: EditText
    private lateinit var postcodeEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var countryEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_address_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("title", "Enter Name")
        dialog?.setTitle(title)

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        view.findViewById<Button>(R.id.address_dialog_done_button).setOnClickListener { dismiss() }
        numberEditText = view.findViewById(R.id.address_dialog_number)
        routeEditText = view.findViewById(R.id.address_dialog_route)
        postcodeEditText = view.findViewById(R.id.address_dialog_postcode)
        cityEditText = view.findViewById(R.id.address_dialog_city)
        countryEditText = view.findViewById(R.id.address_dialog_country)
    }

    override fun onDismiss(dialog: DialogInterface) {
        val number = numberEditText.text.toString().toIntOrNull()
        val route = if (routeEditText.text.isNotEmpty()) routeEditText.text.toString() else null
        val postcode = if (postcodeEditText.text.toString().length >= 4) postcodeEditText.text.toString().toIntOrNull() else null
        val city = if (cityEditText.text.isNotEmpty()) cityEditText.text.toString() else null
        val country = if (countryEditText.text.isNotEmpty()) countryEditText.text.toString() else null

        if (number != null && postcode != null && route != null && city != null && country != null) {
            completionHandler(Address(number, route, city, postcode, country))
        }

        super.onDismiss(dialog)
    }
}