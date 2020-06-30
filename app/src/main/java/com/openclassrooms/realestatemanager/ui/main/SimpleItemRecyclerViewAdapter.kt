package com.openclassrooms.realestatemanager.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate

internal class SimpleItemRecyclerViewAdapter internal constructor(private val mParentActivity: MainActivity,
                                                                  private val estates: List<Estate>,
                                                                  private val mTwoPane: Boolean) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
    private val onClickListener = View.OnClickListener { view ->
        val item = view.tag as Estate
        if (mTwoPane) {
            val arguments = Bundle()
            arguments.putSerializable(MainDetailFragment.ARG_ESTATE, item)
            val fragment = MainDetailFragment()
            fragment.arguments = arguments
            mParentActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
        } else {
            val context = view.context
            val intent = Intent(context, MainDetailActivity::class.java)
            intent.putExtra(MainDetailFragment.ARG_ESTATE, item)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.estate_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.typeTextView.text = estates[position].type.toString()
        holder.cityTextView.text = estates[position].address.city
        holder.priceTextView.text = estates[position].price.toString()
        holder.imageView.setImageBitmap(estates[position].photos[0].photo)
        holder.itemView.setOnClickListener(onClickListener)
        holder.itemView.tag = estates[position]
    }

    override fun getItemCount(): Int {
        return estates.size
    }

    internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.estate_list_row_image)
        val typeTextView: TextView = view.findViewById(R.id.estate_list_row_type)
        val cityTextView: TextView = view.findViewById(R.id.estate_list_row_city)
        val priceTextView: TextView = view.findViewById(R.id.estate_list_row_price)
    }
}