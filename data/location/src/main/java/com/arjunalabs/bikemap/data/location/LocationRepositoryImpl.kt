package com.arjunalabs.bikemap.data.location

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationRepositoryImpl(private val locationManager: LocationManager) : LocationRepository {

    private val _isReceivingUpdates = MutableStateFlow(false)
    override val isReceivingLocationUpdates = _isReceivingUpdates.asStateFlow()

    private val _location = MutableStateFlow<Location?>(null)
    override val location = _location.asStateFlow()

    private val locationListener = Listener()

    @SuppressLint("MissingPermission")
    override fun startLocationUpdates() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
            Companion.MIN_INTERVAL, MIN_DISTANCE, locationListener)
        _isReceivingUpdates.value = true
    }

    override fun stopLocationUpdates() {
        locationManager.removeUpdates(locationListener)
        _isReceivingUpdates.value = false
    }

    private inner class Listener : LocationListener {
        override fun onLocationChanged(location: Location) {
            _location.value = location
        }

    }

    companion object {
        private const val MIN_INTERVAL = 15 * 1000L // 15 ms
        private const val MIN_DISTANCE = 5F // 5 meter
    }
}
