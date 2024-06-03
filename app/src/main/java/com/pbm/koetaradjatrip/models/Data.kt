package com.pbm.koetaradjatrip.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class Data(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val imagePath: String // Path gambar sebagai pengganti byte array
)
