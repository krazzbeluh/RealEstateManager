package com.openclassrooms.realestatemanager.ui.add

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Photo
import java.io.File

class AddPhotosRecyclerViewAdapter(private val parentContext: Context) : RecyclerView.Adapter<AddPhotosRecyclerViewAdapter.ViewHolder>() {
    private var photos: List<Photo> = listOf()
    fun setPhotos(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.photo_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.photoDescription.text = photos[position].description
        Glide.with(parentContext)
                .load(File(photos[0].photo.path ?: ""))
                .into(holder.photoImageView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val photoImageView: ImageView = itemView.findViewById(R.id.photo_row_photo)
        internal val photoDescription: TextView = itemView.findViewById(R.id.photo_row_description)
    }
}