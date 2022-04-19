package com.example.weathersdk.network
import com.example.weathersdk.model.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    //data/2.5/onecall?lat=51.509865&lon=-0.118092&exclude=hourly,minutely&appid=ae1c4977a943a50eaa7da25e6258d8b2&units=Metric&cnt=4
    //"/data/2.5/onecall?{lat=}&{lon=}&exclude=hourly,minutely&{appid=}&{units=}&cnt=4"
    @GET("onecall?exclude=hourly,minutely&cnt=4")
    suspend fun getWeathers(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") tempUnit: String
    ):Response<WeatherResponse>

companion object{
    operator fun invoke(): WeatherApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
            .create(WeatherApi::class.java)
    }
}
}