package com.openclassrooms.realestatemanager.model.estate

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Photo
import java.io.Serializable

@Entity
data class SimpleEstate(@PrimaryKey(autoGenerate = true) val id: Long,
                        @Embedded val address: Address,
                        val type: Estate.EstateType,
                        val price: Int,
                        val rooms: Int,
                        val area: Int,
                        val description: String,
                        val photos: List<Photo>,
                        val agent: String,
                        val sold: Boolean) : Serializable {

    override fun equals(other: Any?) = other is SimpleEstate
            && address == other.address
            && type == other.type
            && price == other.price
            && rooms == other.rooms
            && area == other.area
            && description == other.description
            && photos == other.photos
            && agent == other.agent
            && sold == other.sold

    override fun hashCode(): Int {
        var result = address.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + price
        result = 31 * result + rooms
        result = 31 * result + area
        result = 31 * result + description.hashCode()
        result = 31 * result + photos.hashCode()
        result = 31 * result + agent.hashCode()
        result = 31 * result + sold.hashCode()
        return result
    }
}