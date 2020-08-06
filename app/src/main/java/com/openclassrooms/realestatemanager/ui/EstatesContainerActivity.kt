package com.openclassrooms.realestatemanager.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.main.MainEstateListRecyclerViewAdapter
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment

abstract class EstatesContainerActivity : AppCompatActivity(), ConvertibleActivity {
    override val context: Context = this
    abstract var adapter: MainEstateListRecyclerViewAdapter
    abstract var recyclerView: RecyclerView
    var fragment: EstateDetailFragment? = null
        set(value) {
            field = value
            if (value != null) {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, value)
                        .commit()
            }
        }

    fun convertRecyclerViewItems() {
        for (i in adapter.estates.indices) {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as? MainEstateListRecyclerViewAdapter.ViewHolder
            viewHolder?.convert(isDollar)
        }
    }
}