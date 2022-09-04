package com.arjunalabs.bikemap.data.location

import android.location.Location
import kotlinx.coroutines.flow.StateFlow

interface LocationRepository {

    fun startLocationUpdates()

    fun stopLocationUpdates()

    val location: StateFlow<Location?>

    val isReceivingLocationUpdates: StateFlow<Boolean>
}
