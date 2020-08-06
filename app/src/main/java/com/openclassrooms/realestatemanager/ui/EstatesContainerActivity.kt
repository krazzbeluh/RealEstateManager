package com.openclassrooms.realestatemanager.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.ui.main.MainEstateListRecyclerViewAdapter

abstract class EstatesContainerActivity : AppCompatActivity(), ConvertibleActivity {
    override val context: Context = this
    abstract var adapter: MainEstateListRecyclerViewAdapter
    abstract var recyclerView: RecyclerView

    fun convertRecyclerViewItems() {
        for (i in adapter.estates.indices) {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as? MainEstateListRecyclerViewAdapter.ViewHolder
            viewHolder?.convert(isDollar)
        }
    }
}