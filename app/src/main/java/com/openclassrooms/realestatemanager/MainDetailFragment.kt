package com.openclassrooms.realestatemanager

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.openclassrooms.realestatemanager.dummy.DummyContent
import com.openclassrooms.realestatemanager.dummy.DummyContent.DummyItem

class MainDetailFragment : Fragment() {
    private var mItem: DummyItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments.containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP[arguments.getString(ARG_ITEM_ID)]
            val activity: Activity = this.activity
            val appBarLayout = activity.findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout?
            if (appBarLayout != null) {
                appBarLayout.title = mItem!!.content
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            (rootView.findViewById<View>(R.id.item_detail) as TextView).text = mItem!!.details
        }
        return rootView
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}