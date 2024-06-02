package com.pbm.koetaradjatrip.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pbm.koetaradjatrip.models.Data

@Dao
interface DataDao {

    @Query("SELECT * FROM data ORDER BY id ASC")
    fun getAllData(): PagingSource<Int, Data>

    @Insert
    suspend fun insertData(data: Data)

    @Update
    suspend fun updateData(data: Data)

    @Query("DELETE FROM data WHERE id = :id")
    suspend fun deleteData(id: Int)
}
