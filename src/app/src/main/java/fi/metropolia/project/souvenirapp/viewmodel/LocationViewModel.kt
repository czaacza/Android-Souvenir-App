package fi.metropolia.project.souvenirapp.viewmodel

import android.app.Activity
import android.app.Application
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import fi.metropolia.project.souvenirapp.model.location.LocationManager
import org.osmdroid.util.GeoPoint

class LocationViewModel(application: Application, activity: Activity) :
    AndroidViewModel(application) {
    private val app = application
    private val locationManager = LocationManager(activity)

    val locationPoint = locationManager.locationPoint
    var isLocationTracked = MutableLiveData(false)

    fun startLocationTracking() {
        locationManager.startLocationTracking()
        isLocationTracked.value = true
    }

    fun stopLocationTracking() {
        locationManager.startLocationTracking()
        isLocationTracked.value = false
    }

    fun getAddress(geoPoint: GeoPoint): String {
        val geocoder = Geocoder(app)
        var currentAddress = ""

        currentAddress =
            geocoder.getFromLocation(geoPoint.latitude, geoPoint.longitude, 1)?.first()
                ?.getAddressLine(0) ?: ""

        return currentAddress
    }
}