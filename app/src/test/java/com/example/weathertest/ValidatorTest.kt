package com.example.weathertest

import com.example.weathersdk.network.WeatherApi
import com.example.weathersdk.utils.TempUnit
import com.example.weathersdk.viewmodel.WeatherRepositorey
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule

@RunWith(JUnit4::class)
class ValidatorTest{
    lateinit var repo : WeatherRepositorey
    private val api_key  = "ae1c4977a943a50eaa7da25e6258d8b2"
    private val latitude =27.34345
    private val longitude =77.343543
    lateinit var api: WeatherApi
    private val unit =TempUnit.CELSIUS


    @Before
    fun init() {
        api = WeatherApi.invoke()
        repo = WeatherRepositorey(api,latitude,longitude,api_key,unit)
    }

    @Test
    fun whenLocationisvalid(){
        val latitude:Double = 37.42
        val longitude:Double = 122.08
        val result = Validator.validLatLong(latitude,longitude)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun whenLocationisInvalid(){
        val latitude:Double = 0.0
        val longitude:Double = 0.0
        val result = Validator.validLatLong(latitude,longitude)
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun whenFarhantocelsisus(){
        val result = Validator.fahrenheitToCelsius(11.2)
        val expected_data = 52.16
        assertThat(result).isAtMost(expected_data)
    }

    @Test
    fun whenCrelsisustoFarhan(){
        val result : Double = Validator.celsiusToFahrenheit(10.1)
        val expected_data = -12.16666
        assertThat(result).isAtMost(expected_data)
    }
    @Test
    fun testweatherresponse() = runBlocking{
        val result = Validator.validateweatherresponse(repo.getWeathers().toString())
        assertThat(result).isEqualTo(true)
    }
}