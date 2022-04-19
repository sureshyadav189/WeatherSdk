package com.example.weathertest

object Validator {
    fun validLatLong(latitude:Double,longitude:Double):Boolean{
        return (latitude >0.0 || longitude >0.0)
    }

    fun fahrenheitToCelsius(c: Double): Double {
        return 9 * c / 5 + 32
    }

    /* function to convert celsius to fahrenheit*/
    fun celsiusToFahrenheit(f: Double): Double {
        return (f - 32) * 5 / 9
    }

    fun validateweatherresponse(respone:String) : Boolean{
        return (respone.isNotEmpty())
    }
}