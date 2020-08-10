package com.openclassrooms.realestatemanager.ui

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.add.AddEstateActivity
import com.openclassrooms.realestatemanager.ui.main.MainEstateListRecyclerViewAdapter
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment.Companion.ARG_ESTATE

abstract class EstatesContainerActivity : AppCompatActivity(), ConvertibleActivity {
    override val context: Context = this
    abstract var adapter: MainEstateListRecyclerViewAdapter
    abstract var recyclerView: RecyclerView
    var estate: Estate? = null
    var menu: Menu? = null
    var fragment: EstateDetailFragment? = null
        set(value) {
            field = value
            if (value != null) {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, value)
                        .commit()

                this.menu?.findItem(R.id.menu_edit)?.isVisible = true
            }
        }

    fun convertRecyclerViewItems() {
        for (i in adapter.estates.indices) {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as? MainEstateListRecyclerViewAdapter.ViewHolder
            viewHolder?.convert(isDollar)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_convert -> didTapConvert(item)
            R.id.menu_edit -> {
                if (estate != null) {
                    val intent = Intent(this, AddEstateActivity::class.java)
                    intent.putExtra(ARG_ESTATE, estate)
                    startActivity(intent)
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }
}