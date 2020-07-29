package com.openclassrooms.realestatemanager.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.estate.Estate
import com.openclassrooms.realestatemanager.ui.main.detail.MainDetailActivity
import com.openclassrooms.realestatemanager.ui.main.detail.MainDetailFragment
import com.openclassrooms.realestatemanager.ui.main.detail.MainDetailFragment.Companion.ARG_ESTATE

internal class SimpleItemRecyclerViewAdapter internal constructor(private val parentActivity: MainActivity,
                                                                  private val mTwoPane: Boolean) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
    companion object {
        private var selectedItem: Int? = null
    }

    private var estates = listOf<Estate>()
    fun setEstates(estates: List<Estate>) {
        this.estates = estates
        this.notifyDataSetChanged()
    }

    private val onClickListener = View.OnClickListener { view ->
        val item = view.tag as Estate
        if (mTwoPane) {
            val fragment = MainDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ESTATE, item)
                }
            }
            parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
        } else {
            val context = view.context
            val intent = Intent(context, MainDetailActivity::class.java)
            intent.putExtra(ARG_ESTATE, item)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.estate_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            typeTextView.text = estates[position].estate.type.toString()
            cityTextView.text = estates[position].estate.address.city
            priceTextView.text = estates[position].estate.price.toString()
            itemView.setOnClickListener { view ->
                val item = view.tag as Estate
                if (mTwoPane) {
                    holder.background.setBackgroundColor(ContextCompat.getColor(parentActivity, R.color.colorAccent))
                    selectedItem?.let { notifyItemChanged(it) }
                    selectedItem = position
                    val fragment = MainDetailFragment().apply {
                        arguments = Bundle().apply {
                            putSerializable(ARG_ESTATE, item)
                        }
                    }
                    parentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit()
                } else {
                    val context = view.context
                    val intent = Intent(context, MainDetailActivity::class.java)
                    intent.putExtra(ARG_ESTATE, item)
                    context.startActivity(intent)
                }
            }

            Glide.with(itemView)
                    .load(estates[position].photos[0])
                    .into(imageView)

            itemView.tag = estates[position]
        }
    }

    override fun getItemCount(): Int {
        return estates.size
    }

    internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val background: ConstraintLayout = view.findViewById(R.id.estate_list_content_background)
        val imageView: ImageView = view.findViewById(R.id.estate_list_row_image)
        val typeTextView: TextView = view.findViewById(R.id.estate_list_row_type)
        val cityTextView: TextView = view.findViewById(R.id.estate_list_row_city)
        val priceTextView: TextView = view.findViewById(R.id.estate_list_row_price)
    }
}