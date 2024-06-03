package com.pbm.koetaradjatrip.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pbm.koetaradjatrip.database.DataDao
import com.pbm.koetaradjatrip.models.Data
import kotlinx.coroutines.flow.Flow

class DataRepository(private val dataDao: DataDao) {

    fun getAllData(): Flow<PagingData<Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = 60,  // Menentukan ukuran halaman
                enablePlaceholders = false
            ),
            pagingSourceFactory = { dataDao.getAllData() }
        ).flow
    }

    suspend fun insert(data: Data) {
        dataDao.insert(data)
    }

    suspend fun deleteData(data: Data) {
        dataDao.deleteData(data)
    }

    suspend fun deleteAll() {
        dataDao.deleteAll()
    }
}
