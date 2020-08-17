package com.openclassrooms.realestatemanager.ui.main.detail

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.ConvertibleActivity
import com.openclassrooms.realestatemanager.ui.add.AddEstateActivity
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment.Companion.ARG_ESTATE
import com.openclassrooms.realestatemanager.utils.PREFERENCES_NAME

class MainDetailActivity : AppCompatActivity(), ConvertibleActivity {
    override val context: Context = this
    override lateinit var preferences: SharedPreferences
    private lateinit var fragment: EstateDetailFragment
    private lateinit var estate: Estate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_detail)

        preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        if (savedInstanceState == null) {
            estate = intent.getSerializableExtra(ARG_ESTATE) as? Estate
                    ?: throw NullPointerException("Estate should not be null !")
            val arguments = Bundle()
            arguments.putSerializable(ARG_ESTATE,
                    estate)
            fragment = EstateDetailFragment(isDollar)
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.convert_menu, menu)
        menu?.findItem(R.id.menu_convert)?.setIcon(if (isDollar) R.drawable.euro else R.drawable.dollar)
        menu?.findItem(R.id.menu_edit)?.isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_convert -> didTapConvert(item)
            R.id.menu_edit -> {
                val intent = Intent(this, AddEstateActivity::class.java)
                intent.putExtra(ARG_ESTATE, estate)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun didTapConvert(item: MenuItem) {
        super.didTapConvert(item)
        fragment.convert(isDollar)
    }
}