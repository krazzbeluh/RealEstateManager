package com.openclassrooms.realestatemanager.ui.add

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.estate.AssociatedPOI
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.dialog.AddressDialogFragment
import com.openclassrooms.realestatemanager.ui.dialog.photo.PhotoDialogFragment
import com.openclassrooms.realestatemanager.utils.toEditable

class AddEstateActivity : AppCompatActivity() {
    companion object {
        @Suppress("unused")
        val TAG = AddEstateActivity::class.java.simpleName
    }

    private lateinit var viewModel: AddEstateViewModel
    private val photos = MutableLiveData(mutableListOf<Photo>())

    var address: Address? = null
    private lateinit var typeSpinner: Spinner
    private lateinit var priceEditText: EditText
    private lateinit var roomsEditText: EditText
    private lateinit var areaEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var agentEditText: EditText
    private lateinit var soldSwitch: Switch

    private lateinit var schoolChip: Chip
    private lateinit var parkChip: Chip
    private lateinit var supermarketChip: Chip
    private lateinit var photosRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estate)

        val viewModelFactory = Injection.provideViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddEstateViewModel::class.java)

        findViewById<EditText>(R.id.add_estate_address).apply {
            isFocusable = false
            isClickable = true
            setOnClickListener {
                val addressDialog = AddressDialogFragment.newInstance { address ->
                    this@AddEstateActivity.address = address
                    text = address.toString().toEditable()
                }
                addressDialog.show(supportFragmentManager, "fragment_address")
            }
        }

        typeSpinner = findViewById<Spinner>(R.id.add_estate_type).apply {
            adapter = ArrayAdapter<Estate.EstateType>(this@AddEstateActivity, android.R.layout.simple_list_item_1, Estate.EstateType.values())
        }
        priceEditText = findViewById(R.id.add_estate_price)
        roomsEditText = findViewById(R.id.add_estate_rooms)
        areaEditText = findViewById(R.id.add_estate_area)
        descriptionEditText = findViewById(R.id.add_estate_description)
        agentEditText = findViewById(R.id.add_estate_agent)
        soldSwitch = findViewById(R.id.add_estate_sold)
        schoolChip = findViewById(R.id.add_estate_school)
        parkChip = findViewById(R.id.add_estate_park)
        supermarketChip = findViewById(R.id.add_estate_supermarket)
        photosRecyclerView = findViewById<RecyclerView>(R.id.add_estate_photos_recyclerview).apply {
            layoutManager = LinearLayoutManager(this@AddEstateActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = AddPhotosRecyclerViewAdapter(this@AddEstateActivity)

            photos.observe(this@AddEstateActivity, Observer { photos ->
                if (adapter is AddPhotosRecyclerViewAdapter)
                    (adapter as AddPhotosRecyclerViewAdapter).setPhotos(photos)
            })
        }
    }

    fun addPhotoButtonClicked(@Suppress("UNUSED_PARAMETER") v: View) {
        PhotoDialogFragment.newInstance { photo ->
            val photos = this.photos.value
            photos?.add(photo)
            this.photos.value = photos
        }.show(supportFragmentManager, "fragment_photo")
    }

    fun addButtonClicked(@Suppress("UNUSED_PARAMETER") v: View) {
        val type = typeSpinner.selectedItem as? Estate.EstateType
        val price = priceEditText.text.toString().toIntOrNull()
        val rooms = roomsEditText.text.toString().toIntOrNull()
        val area = areaEditText.text.toString().toIntOrNull()
        val description = descriptionEditText.text.toString()
        val agent = agentEditText.text.toString()
        val isSold = soldSwitch.isActivated
        viewModel.addEstate(address, type, price, rooms, area, description, listOf(), agent, getPois(), isSold)
    }

    private fun getPois(): List<AssociatedPOI.POI> {
        val pois = mutableListOf<AssociatedPOI.POI>()
        if (schoolChip.isChecked) pois.add(AssociatedPOI.POI.SCHOOL)
        if (parkChip.isChecked) pois.add(AssociatedPOI.POI.PARK)
        if (supermarketChip.isChecked) pois.add(AssociatedPOI.POI.SUPERMARKET)
        return pois
    }
}