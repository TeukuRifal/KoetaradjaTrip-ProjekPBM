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
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { dataDao.getAllData() }
        ).flow
    }

    suspend fun insertData(data: Data) {
        dataDao.insertData(data)
    }

    suspend fun updateData(data: Data) {
        dataDao.updateData(data)
    }

    suspend fun deleteData(id: Data) {
        dataDao.deleteData(id)
    }
}
