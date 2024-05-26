package com.pbm.koetaradjatrip.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pbm.koetaradjatrip.database.DatabaseRoom
import com.pbm.koetaradjatrip.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DataRepository
    val allData: LiveData<List<Data>>

    init {
        val dao = DatabaseRoom.getDatabase(application).dataDao()
        repository = DataRepository(dao)
        allData = repository.allData
    }

    fun insertData(data: Data) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertData(data)
    }
}