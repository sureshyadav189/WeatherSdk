package com.example.weathertest

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weathersdk.network.WeatherApi
import com.example.weathersdk.utils.TempUnit
import com.example.weathersdk.viewmodel.WeatherRepositorey
import com.example.weathertest.databinding.ActivityMainBinding
import com.example.weathertest.model.LatLongModel
import com.example.weathertest.viewmodel.MainRepositorey
import com.example.weathertest.viewmodel.MainViewModel
import com.example.weathertest.viewmodel.MainViewModelFactorey
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    private val PERMISSION_ID = 44
    private lateinit var viewModel: MainViewModel
    private lateinit var factorey: MainViewModelFactorey
    lateinit var repositorey: MainRepositorey
    lateinit var latlonlist: List<LatLongModel>
    var latitude:Double=0.0
    var longitude:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(checkPermissions()){
            repositorey = MainRepositorey(this)
            factorey = MainViewModelFactorey(repositorey)
            viewModel = ViewModelProvider(this,factorey).get(MainViewModel::class.java)
            init()
        }
        else{
            requestPermissions()
        }


    }
    private fun init(){
        getlastlocation()
    }


    private fun getlastlocation(){
        viewModel.latLongdata.observe(this, Observer {
            //latlonlist = it
            latitude = it.get(0).latitude
            longitude = it.get(0).longitude
            if(Validator.validLatLong(latitude,longitude).equals(false)){
                return@Observer
            }
            binding.content.tvLocation.text ="latitude ${it.get(0).latitude},longitude ${it.get(0).longitude}"

            //Weather SDk
            val api_key:String =getString(R.string.key)
            val repositorey = WeatherRepositorey(WeatherApi(),it.get(0).latitude,it.get(0).longitude,api_key,TempUnit.CELSIUS)
            GlobalScope.launch(Dispatchers.Main)
            {
                val weather = CoroutineScope(Dispatchers.IO).async {
                    repositorey.getWeathers()
                }.await()
                showdata(weather.toString())
            }

        })
    }

    fun showdata(data:String){
        binding.content.tvWeatherData.text = data.toString()
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    override
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getlastlocation()
            } else {
                repositorey.goToLocationSettings()
            }
        }
    }

    // method to request for permissions
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
        init()
    }
}