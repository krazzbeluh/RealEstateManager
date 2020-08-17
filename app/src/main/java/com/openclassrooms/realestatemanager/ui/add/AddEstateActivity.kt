package com.openclassrooms.realestatemanager.ui.add

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.switchmaterial.SwitchMaterial
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.estate.AssociatedPOI
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.ConvertibleActivity
import com.openclassrooms.realestatemanager.ui.PhotosRecyclerViewAdapter
import com.openclassrooms.realestatemanager.ui.dialog.AddressDialogFragment
import com.openclassrooms.realestatemanager.ui.dialog.photo.PhotoDialogFragment
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment
import com.openclassrooms.realestatemanager.utils.PREFERENCES_NAME
import com.openclassrooms.realestatemanager.utils.convertToDollar
import com.openclassrooms.realestatemanager.utils.showAlert
import com.openclassrooms.realestatemanager.utils.toEditable

class AddEstateActivity : AppCompatActivity(), ConvertibleActivity {
    companion object {
        @Suppress("unused")
        val TAG = AddEstateActivity::class.java.simpleName
    }

    override val context = this as Context
    override lateinit var preferences: SharedPreferences

    private lateinit var viewModel: AddEstateViewModel
    private var estate: Estate? = null
    private val photos = MutableLiveData(mutableListOf<Photo>())

    var address: Address? = null
    private lateinit var typeSpinner: Spinner
    private lateinit var spinnerAdapter: ArrayAdapter<Estate.EstateType>
    private lateinit var priceTextView: TextView
    private lateinit var priceEditText: EditText
    private lateinit var roomsEditText: EditText
    private lateinit var areaEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var agentEditText: EditText
    private lateinit var soldSwitch: SwitchMaterial

    private lateinit var schoolChip: Chip
    private lateinit var parkChip: Chip
    private lateinit var supermarketChip: Chip
    private lateinit var photosRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estate)

        preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        spinnerAdapter = ArrayAdapter(this@AddEstateActivity, android.R.layout.simple_list_item_1, Estate.EstateType.values())

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
            adapter = spinnerAdapter
        }
        priceEditText = findViewById(R.id.add_estate_price)
        priceTextView = findViewById<TextView>(R.id.add_estate_price_text_view).apply {
            text = getString(if (isDollar) R.string.price_dollar else R.string.price_euro)
        }
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
            adapter = PhotosRecyclerViewAdapter(this@AddEstateActivity)

            photos.observe(this@AddEstateActivity, Observer { photos ->
                if (adapter is PhotosRecyclerViewAdapter)
                    (adapter as PhotosRecyclerViewAdapter).setPhotos(photos)
            })
        }

        estate = (intent.getSerializableExtra(EstateDetailFragment.ARG_ESTATE) as? Estate).apply {
            if (this == null) return@apply
            this@AddEstateActivity.photos.value?.addAll(this.photos)
            this@AddEstateActivity.address = this.estate.address
            this@AddEstateActivity.typeSpinner.setSelection(spinnerAdapter.getPosition(estate.type))
            this@AddEstateActivity.priceEditText.setText(estate.price.toString())
            this@AddEstateActivity.roomsEditText.setText(estate.rooms.toString())
            this@AddEstateActivity.areaEditText.setText(estate.area.toString())
            this@AddEstateActivity.descriptionEditText.setText(estate.description)
            this@AddEstateActivity.agentEditText.setText(estate.agent)
            this@AddEstateActivity.soldSwitch.isActivated = estate.sold
            this.nearbyPointsOfInterests.forEach { poi ->
                if (poi.poi == AssociatedPOI.POI.SCHOOL) this@AddEstateActivity.schoolChip.isChecked = true
                if (poi.poi == AssociatedPOI.POI.PARK) this@AddEstateActivity.parkChip.isChecked = true
                if (poi.poi == AssociatedPOI.POI.SUPERMARKET) this@AddEstateActivity.supermarketChip.isChecked = true
            }
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
        val photos = this.photos.value
        val agent = agentEditText.text.toString()
        val isSold = soldSwitch.isActivated

        if (photos != null
                && address != null
                && type != null
                && price != null
                && rooms != null
                && area != null
                && description.isNotEmpty()
                && photos.isNotEmpty()
                && agent.isNotEmpty()
                && getPois().isNotEmpty()) {
            viewModel.addEstate(address, type, if (isDollar) price else price.convertToDollar(), rooms, area, description, photos, agent, getPois(), isSold, estate?.estate?.id)
            finish()
        } else showAlert(getString(R.string.error), getString(R.string.complete_form))
    }

    private fun getPois(): List<AssociatedPOI.POI> {
        val pois = mutableListOf<AssociatedPOI.POI>()
        if (schoolChip.isChecked) pois.add(AssociatedPOI.POI.SCHOOL)
        if (parkChip.isChecked) pois.add(AssociatedPOI.POI.PARK)
        if (supermarketChip.isChecked) pois.add(AssociatedPOI.POI.SUPERMARKET)
        return pois
    }

    override fun didTapConvert(item: MenuItem) {
        super.didTapConvert(item)
        priceTextView.text = getString(if (isDollar) R.string.price_dollar else R.string.price_euro)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.convert_menu, menu)
        menu?.findItem(R.id.menu_convert)?.setIcon(if (isDollar) R.drawable.euro else R.drawable.dollar)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_convert -> didTapConvert(item)
        }
        return super.onOptionsItemSelected(item)
    }
}