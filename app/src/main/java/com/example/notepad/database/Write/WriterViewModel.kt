package com.example.notepad.database.Write

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriterViewModel() : ViewModel() {
    var listWrites : MutableLiveData<List<Write>> = MutableLiveData()

    init {

    }

    fun updateListWrites(){
        listWrites.value = listOf()
    }
}