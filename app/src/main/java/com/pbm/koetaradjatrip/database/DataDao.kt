package com.pbm.koetaradjatrip.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pbm.koetaradjatrip.models.Data

@Dao
interface DataDao {
    @Insert
    suspend fun insertData(data: Data)

    @Query("SELECT * FROM data")
    fun getAllData(): LiveData<List<Data>>
}