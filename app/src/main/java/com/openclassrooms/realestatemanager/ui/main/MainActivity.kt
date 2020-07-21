package com.openclassrooms.realestatemanager.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.ui.add.AddEstateActivity
import com.openclassrooms.realestatemanager.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SimpleItemRecyclerViewAdapter

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
        val recyclerView = findViewById<View>(R.id.item_list)!!
        setupRecyclerView(recyclerView as RecyclerView)
        checkAddressForEstates()
    }

    fun addEstateButtonClicked(@Suppress("UNUSED_PARAMETER") v: View) = startActivity(Intent(this, AddEstateActivity::class.java))

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = SimpleItemRecyclerViewAdapter(this, mTwoPane)
        recyclerView.adapter = adapter
        viewModel.getEstates().observe(this, Observer { estates ->
            adapter.setEstates(estates)
        })
    }

    private fun checkAddressForEstates() {
        viewModel.getEstates().observe(this, Observer { estates ->
            if (!Utils.isInternetAvailable(this)) return@Observer
            val estatesToGetLocation = mutableListOf<Estate>()
            for (estate in estates) if (estate.address.getLocation() == null) {
                estatesToGetLocation.add(estate)
            }

            Log.d(TAG, "checkAddressForEstates: ${estatesToGetLocation.size}")
            coroutineScope.launch(Dispatchers.IO) {
                viewModel.checkAddresses(estatesToGetLocation)
            }
        })
    }
}