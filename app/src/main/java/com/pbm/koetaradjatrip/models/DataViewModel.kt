package com.pbm.koetaradjatrip.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pbm.koetaradjatrip.database.DatabaseRoom
import com.pbm.koetaradjatrip.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DataViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DataRepository

    init {
        val dataDao = DatabaseRoom.getDatabase(application).dataDao()
        repository = DataRepository(dataDao)
    }

    fun getAllData(): Flow<PagingData<Data>> {
        return repository.getAllData().cachedIn(viewModelScope)
    }

    fun insertDataFromImage(data: Data) = viewModelScope.launch {
        repository.insert(data)
    }

    fun deleteData(data: Data) = viewModelScope.launch {
        repository.deleteData(data)
    }

    fun deleteAllData() = viewModelScope.launch {
        repository.deleteAll()
    }
}
