package com.example.weathertest.viewmodel

import android.R.attr.data
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weathertest.model.LatLongModel
import com.google.android.gms.location.*


class MainRepositorey(val applicationContext: Context) {
    var fusedLocationProviderClient: FusedLocationProviderClient?=null
    private var latitude = 0.0
    private var longitude = 0.0


    private val _latlondata = MutableLiveData<List<LatLongModel>>()
    val location_data : LiveData<List<LatLongModel>>
    get() =_latlondata

    suspend fun getCurrentLoation(){
        getLocation()
    }

    @SuppressLint("MissingPermission")
    public fun getLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if(isLocationEnabled()){
            fusedLocationProviderClient?.lastLocation?.addOnCompleteListener { task ->
            val location =task.result
                if(location == null){
                    requestNewLocationData()
                }
                else{
                    setLocation(location)
                }
            }
        }
        else{
            goToLocationSettings()
        }
    }

    public fun goToLocationSettings() {
        Toast.makeText(applicationContext, "Please turn on your location...", Toast.LENGTH_LONG)
            .show()
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        applicationContext.startActivity(intent)
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        fusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()!!
        )
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            setLocation(mLastLocation)
        }
    }

    private fun setLocation(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
        val latlonglistdata: MutableList<LatLongModel> = ArrayList()
        latlonglistdata.add(
            LatLongModel(latitude,longitude)
        )
        _latlondata.setValue(latlonglistdata)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )

    }


}