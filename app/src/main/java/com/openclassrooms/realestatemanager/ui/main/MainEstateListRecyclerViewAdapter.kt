package com.openclassrooms.realestatemanager.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.EstatesContainerActivity
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment
import com.openclassrooms.realestatemanager.ui.main.detail.EstateDetailFragment.Companion.ARG_ESTATE
import com.openclassrooms.realestatemanager.ui.main.detail.MainDetailActivity
import com.openclassrooms.realestatemanager.utils.convertToEuro
import java.io.File

class MainEstateListRecyclerViewAdapter internal constructor(private val parentActivity: EstatesContainerActivity,
                                                             private val mTwoPane: Boolean) : RecyclerView.Adapter<MainEstateListRecyclerViewAdapter.ViewHolder>() {
    companion object {
        private var selectedItem: Int? = null
    }

    var estates = listOf<Estate>()
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.estate_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            estate = estates[position]
            typeTextView.text = estate.estate.type.toString()
            cityTextView.text = estate.estate.address.city
            convert(parentActivity.isDollar)
            itemView.setOnClickListener { view ->
                val item = view.tag as Estate
                if (mTwoPane) {
                    holder.background.setBackgroundColor(ContextCompat.getColor(parentActivity, R.color.colorAccent))
                    selectedItem?.let { notifyItemChanged(it) }
                    selectedItem = position
                    val fragment = EstateDetailFragment(parentActivity.isDollar).apply {
                        arguments = Bundle().apply {
                            putSerializable(ARG_ESTATE, item)
                        }
                    }
                    parentActivity.fragment = fragment
                } else {
                    val context = view.context
                    val intent = Intent(context, MainDetailActivity::class.java)
                    intent.putExtra(ARG_ESTATE, item)
                    context.startActivity(intent)
                }
            }
            imageView.setImageBitmap(fetchPhoto(position))
            itemView.tag = estate
        }
    }

    override fun getItemCount(): Int {
        return estates.size
    }

    private fun fetchPhoto(position: Int): Bitmap? {
        val directory = parentActivity.filesDir
        val file = File(directory, estates[position].photos[0].fileName)
        return BitmapFactory.decodeFile(file.toString())
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var estate: Estate
        val background: ConstraintLayout = view.findViewById(R.id.estate_list_content_background)
        val imageView: ImageView = view.findViewById(R.id.estate_list_row_image)
        val typeTextView: TextView = view.findViewById(R.id.estate_list_row_type)
        val cityTextView: TextView = view.findViewById(R.id.estate_list_row_city)
        private val priceTextView: TextView = view.findViewById(R.id.estate_list_row_price)

        @SuppressLint("SetTextI18n")
        fun convert(toDollar: Boolean) {
            if (toDollar) priceTextView.text = "${estate.estate.price} $"
            else priceTextView.text = "${estate.estate.price.convertToEuro()} €"
        }
    }
}