package com.example.weathertest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertest.model.LatLongModel
import kotlinx.coroutines.launch

class MainViewModel(private val repositorey: MainRepositorey):ViewModel() {
    init {
        viewModelScope.launch {
            repositorey.getCurrentLoation()
        }
    }

    val  latLongdata : LiveData<List<LatLongModel>>
    get() = repositorey.location_data
}