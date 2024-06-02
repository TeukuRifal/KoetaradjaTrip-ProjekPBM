package com.pbm.koetaradjatrip.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.pbm.koetaradjatrip.database.DatabaseRoom
import com.pbm.koetaradjatrip.models.Data
import com.pbm.koetaradjatrip.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DataViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DataRepository
    val allData: Flow<PagingData<Data>>

    init {
        val dao = DatabaseRoom.getDatabase(application).dataDao()
        repository = DataRepository(dao)
        allData = repository.getAllData()
    }

    fun insertDataFromImage(data: Data) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(data)
        }
    }

    fun deleteData(data: Data) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(data)
        }
    }
}
