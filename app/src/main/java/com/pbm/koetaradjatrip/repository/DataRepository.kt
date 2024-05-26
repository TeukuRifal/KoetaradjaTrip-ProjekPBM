package com.pbm.koetaradjatrip.repository

import androidx.lifecycle.LiveData
import com.pbm.koetaradjatrip.database.DataDao
import com.pbm.koetaradjatrip.models.Data

class DataRepository(private val dataDao: DataDao) {

    val allData: LiveData<List<Data>> = dataDao.getAllData()

    suspend fun insertData(data: Data) {
        dataDao.insertData(data)
    }
}