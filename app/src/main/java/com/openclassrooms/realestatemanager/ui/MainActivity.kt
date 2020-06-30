package com.openclassrooms.realestatemanager.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.openclassrooms.realestatemanager.AddEstateActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Photo

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [MainDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : AppCompatActivity() {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
    }

    fun addEstateButtonClicked(@Suppress("UNUSED_PARAMETER") v: View) = startActivity(Intent(this, AddEstateActivity::class.java))

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, listOf(Estate(0,
                Address(26, "Rue Gustave Flaubert", "Dieppe", 76200,
                        "France"), Estate.EstateType.HOUSE, 128000, 3, 80,
                "Petite maison à Dieppe", listOf(Photo(null, "Super photo")),
                listOf(), "Paul Leclerc")),
                mTwoPane)
    }

    private class SimpleItemRecyclerViewAdapter internal constructor(private val mParentActivity: MainActivity,
                                                                     private val mValues: List<Estate>,
                                                                     private val mTwoPane: Boolean) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
        private val mOnClickListener = View.OnClickListener { view ->
            val item = view.tag as Estate
            if (mTwoPane) {
                val arguments = Bundle()
                arguments.putString(MainDetailFragment.ARG_ESTATE_ID, item.id.toString())
                val fragment = MainDetailFragment()
                fragment.arguments = arguments
                mParentActivity.supportFragmentManager.beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
            } else {
                val context = view.context
                val intent = Intent(context, MainDetailActivity::class.java)
                intent.putExtra(MainDetailFragment.ARG_ESTATE_ID, item.id)
                context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.estate_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mIdView.text = mValues[position].id.toString()
            holder.itemView.tag = mValues[position]
            holder.itemView.setOnClickListener(mOnClickListener)
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mIdView: TextView = view.findViewById<View>(R.id.estate_list_row_type) as TextView
            val mContentView: TextView = view.findViewById<View>(R.id.estate_list_row_city) as TextView

        }

    }
}