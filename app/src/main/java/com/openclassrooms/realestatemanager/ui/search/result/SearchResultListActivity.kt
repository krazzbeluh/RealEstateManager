package com.openclassrooms.realestatemanager.ui.search.result

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.EstatesContainerActivity
import com.openclassrooms.realestatemanager.ui.main.MainEstateListRecyclerViewAdapter
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment
import com.openclassrooms.realestatemanager.utils.showAlert

class SearchResultListActivity : EstatesContainerActivity() {
    companion object {
        @Suppress("unused")
        private val TAG = SearchResultListActivity::class.java.simpleName
    }

    private lateinit var estates: List<Estate>
    override lateinit var adapter: MainEstateListRecyclerViewAdapter
    override lateinit var recyclerView: RecyclerView
    override lateinit var preferences: SharedPreferences

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result_list)

        preferences = getPreferences(Context.MODE_PRIVATE)

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
            mTwoPane = true
        }
        recyclerView = findViewById(R.id.searchresultactivity_list)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = MainEstateListRecyclerViewAdapter(this, mTwoPane)
        recyclerView.adapter = adapter
        adapter.estates = estates
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.convert_menu, menu)
        menu?.getItem(0)?.setIcon(if (isDollar) R.drawable.euro else R.drawable.dollar)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_convert -> didTapConvert(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun didTapConvert(item: MenuItem) {
        super.didTapConvert(item)
        convertRecyclerViewItems()
    }

    lateinit var estate: Estate
}