package com.openclassrooms.realestatemanager.ui.search.result

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.main.MainEstateListRecyclerViewAdapter
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment
import com.openclassrooms.realestatemanager.utils.showAlert

class SearchResultListActivity : AppCompatActivity() {
    companion object {
        @Suppress("unused")
        private val TAG = SearchResultListActivity::class.java.simpleName
    }

    private lateinit var estates: List<Estate>
    private lateinit var adapter: MainEstateListRecyclerViewAdapter

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result_list)

        @Suppress("UNCHECKED_CAST") val extras = intent.getSerializableExtra(EstateDetailFragment.ARG_ESTATE) as? List<Estate>
        if (extras != null) {
            estates = extras
        } else {
            showAlert(getString(R.string.error), getString(R.string.error_occurred))
            finish()
            return
        }

        val toolbar = findViewById<Toolbar>(R.id.search_result_toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title
        if (findViewById<View?>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }
        val recyclerView = findViewById<RecyclerView>(R.id.searchresultactivity_list)
        setupRecyclerView(recyclerView)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = MainEstateListRecyclerViewAdapter(this, mTwoPane)
        recyclerView.adapter = adapter
        adapter.setEstates(estates)
    }

    lateinit var estate: Estate
}