package com.example.weathersdk.utils

object TempUtils {
fun getconvertedTempUnit(tempUnit: TempUnit):String{
    return when (tempUnit.name){
        TempUnit.CELSIUS.name ->{
            "metric"
        }
        TempUnit.FAHRENHEIT.name ->{
            "imperial"
        }
        else ->{
            "standard"
        }
    }
}
}