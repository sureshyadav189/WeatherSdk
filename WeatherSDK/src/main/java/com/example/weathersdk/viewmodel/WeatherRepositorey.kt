package com.example.weathersdk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weathersdk.model.WeatherResponse
import com.example.weathersdk.network.SafeApiRequest
import com.example.weathersdk.network.WeatherApi
import com.example.weathersdk.utils.TempUnit
import com.example.weathersdk.utils.TempUtils

class WeatherRepositorey(
    private val api: WeatherApi,
    private val latitude:Double,
    private val longitude:Double,
    private val api_key:String,
    private val unit:TempUnit
): SafeApiRequest() {
    private val _weatherres = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse>
        get() = _weatherres

        suspend fun getWeathers() = apiRequest{
            api.getWeathers(latitude,longitude,api_key,convertTempUnit(unit))
        }

     fun convertTempUnit(tempUnit: TempUnit): String {
        return TempUtils.getconvertedTempUnit(tempUnit)
    }
}