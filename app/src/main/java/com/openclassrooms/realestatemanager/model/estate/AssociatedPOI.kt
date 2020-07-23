package com.openclassrooms.realestatemanager.model.estate

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(foreignKeys = [ForeignKey(entity = SimpleEstate::class,
        parentColumns = ["id"],
        childColumns = ["estateId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)])
data class AssociatedPOI(
        @PrimaryKey(autoGenerate = true) val id: Long,
        var estateId: Long,
        val poi: POI
) : Serializable {
    enum class POI {
        SCHOOL,
        PARK,
        SUPERMARKET;
    }

    override fun equals(other: Any?) = other is AssociatedPOI && poi == other.poi
    override fun hashCode(): Int {
        return poi.hashCode()
    }
}