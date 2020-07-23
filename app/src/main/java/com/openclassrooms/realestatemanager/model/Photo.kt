package com.openclassrooms.realestatemanager.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.model.estate.SimpleEstate
import java.io.Serializable

@Entity(foreignKeys = [ForeignKey(entity = SimpleEstate::class,
        parentColumns = ["id"],
        childColumns = ["estateId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)])
data class Photo(@PrimaryKey(autoGenerate = true) val id: Long, var estateId: Long, val photo: Uri, val description: String) : Serializable