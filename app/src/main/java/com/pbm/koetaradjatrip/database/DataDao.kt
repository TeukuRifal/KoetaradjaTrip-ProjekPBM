package com.pbm.koetaradjatrip.database

import androidx.paging.PagingSource
import androidx.room.*
import com.pbm.koetaradjatrip.models.Data

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: Data)

    @Query("SELECT * FROM data ORDER BY id ASC")
    fun getAllData(): PagingSource<Int, Data>

    @Delete
    suspend fun deleteData(data: Data)

    @Query("DELETE FROM data")
    suspend fun deleteAll()
}
