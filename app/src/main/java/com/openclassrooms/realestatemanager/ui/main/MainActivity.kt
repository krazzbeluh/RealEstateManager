package com.openclassrooms.realestatemanager.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.add.AddEstateActivity
import com.openclassrooms.realestatemanager.ui.map.MapActivity
import com.openclassrooms.realestatemanager.ui.search.AdvancedSearchActivity
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.showAlert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName

        private const val GET_LOCATION_PERMISSION = 229
        private const val LOCATION_PERM = Manifest.permission.ACCESS_FINE_LOCATION
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainEstateListRecyclerViewAdapter

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelFactory = Injection.provideViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title
        if (findViewById<View?>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }
        val recyclerView = findViewById<RecyclerView>(R.id.item_list)
        setupRecyclerView(recyclerView)
        checkAddressForEstates()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_map -> openMapActivity()
            R.id.menu_search -> openSearchActivity()
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

    fun addEstateButtonClicked(@Suppress("UNUSED_PARAMETER") v: View) = startActivity(Intent(this, AddEstateActivity::class.java))

    @AfterPermissionGranted(GET_LOCATION_PERMISSION)
    private fun openMapActivity() {
        if (EasyPermissions.hasPermissions(this, LOCATION_PERM)) {
            if (Utils.isLocationEnabled(this)) {
                if (Utils.isInternetAvailable(this)) {
                    val intent = Intent(this, MapActivity::class.java)
                    startActivity(intent)
                } else {
                    showAlert(getString(R.string.internet_required), getString(R.string.internet_required_detail))
                }
            } else {
                showAlert(
                        getString(R.string.required_location),
                        getString(R.string.required_location_detail)
                )
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.required_location), GET_LOCATION_PERMISSION, LOCATION_PERM)
        }
    }

    private fun openSearchActivity() {
        val intent = Intent(this, AdvancedSearchActivity::class.java)
        startActivity(intent)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = MainEstateListRecyclerViewAdapter(this, mTwoPane)
        recyclerView.adapter = adapter
        viewModel.getEstates().observe(this, Observer { estates ->
            adapter.setEstates(estates)
        })
    }

    lateinit var estate: Estate

    private fun checkAddressForEstates() {
        viewModel.getEstates().observe(this, Observer { estates ->
            if (!Utils.isInternetAvailable(this)) return@Observer
            val estatesToGetLocation = mutableListOf<Estate>()
            for (estate in estates) if (estate.estate.address.getLocation() == null) {
                estatesToGetLocation.add(estate)
            }

            Log.d(TAG, "checkAddressForEstates: ${estatesToGetLocation.size}")
            coroutineScope.launch(Dispatchers.IO) {
                viewModel.checkAddresses(estatesToGetLocation)
            }
        })
    }
}