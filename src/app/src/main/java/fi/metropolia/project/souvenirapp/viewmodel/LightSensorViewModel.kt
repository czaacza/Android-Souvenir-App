package fi.metropolia.project.souvenirapp.viewmodel

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.MutableLiveData

class LightSensorViewModel(val sensorManager: SensorManager) {

    var sunSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    var sunData = MutableLiveData<Float>()

    val sensorEventListener = object : SensorEventListener{
        override fun onSensorChanged(sensorEvent: SensorEvent?) {
            if (sensorEvent == null){
                return
            }
            if (sensorEvent.sensor == sunSensor){
                sunData.postValue(sensorEvent.values[0])
                /*if(sunData.value == null) {
                    sunData.postValue(sensorEvent.values[0])
                }
                else if(sunData.value!! - sensorEvent.values[0] ==){
                    sunData.postValue(sensorEvent.values[0])

                }*/

            }
        }
        override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {
            Log.d("DBG", "Accuracy changed")
        }
    }
}