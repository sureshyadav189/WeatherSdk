package com.example.weathersdk.model
import com.example.weathersdk.Current

data class WeatherResponse(
    val current: Current,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)