package fi.metropolia.project.souvenirapp.model.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import org.osmdroid.util.GeoPoint

const val INTERVAL_TIME = 6000L

class LocationManager(val activityContext: Activity) {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    var locationPoint = MutableLiveData<GeoPoint>()


    init {
        requestPermissions()
        setFusedLocationClient()
        setLocationRequest()
        setLocationCallback()
    }


    fun startLocationTracking() {
        if (ActivityCompat.checkSelfPermission(
                activityContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    fun stopLocationTracking() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun setFusedLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activityContext)
    }

    private fun setLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.lastLocation == null) {
                    return
                }
                locationPoint.value =
                    GeoPoint(
                        locationResult.lastLocation!!.latitude,
                        locationResult.lastLocation!!.longitude
                    )
            }
        }
    }

    private fun setLocationRequest() {
        locationRequest = LocationRequest
            .create()
            .setInterval(INTERVAL_TIME)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
    }


    private fun requestPermissions() {
        if ((Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                activityContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                activityContext,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }

//        if ((Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
//                activityContext,
//                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED)
//        ) {
//            ActivityCompat.requestPermissions(
//                activityContext,
//                arrayOf(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION),
//                0
//            )
//        }
//
//        if ((Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
//                activityContext,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED)
//        ) {
//            ActivityCompat.requestPermissions(
//                activityContext,
//                arrayOf(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION),
//                0
//            )
//        }
    }

}