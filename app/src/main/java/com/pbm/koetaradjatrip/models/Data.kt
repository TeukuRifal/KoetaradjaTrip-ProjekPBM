package com.pbm.koetaradjatrip.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class Data(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val image: ByteArray // Assuming the image is stored as a byte array
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        return image.contentEquals(other.image)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}
