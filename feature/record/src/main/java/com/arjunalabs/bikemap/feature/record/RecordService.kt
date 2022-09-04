package com.arjunalabs.bikemap.feature.record

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.arjunalabs.bikemap.data.location.LocationRepository
import com.arjunalabs.bikemap.data.location.LocationRepositoryImpl
import com.arjunalabs.bikemap.utility.notification.*
import com.arjunalabs.bikemap.utility.permission.hasPermission
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecordService : LifecycleService(), LocationUpdateService {

    private var locationRepository: LocationRepository? = null

    private var started = false
    private var isForeground = false

    private val localBinder = LocalBinder()
    private var bindCount = 0
    private fun isBound() = bindCount > 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationRepository == null) {
            locationRepository = LocationRepositoryImpl(locationManager)
        }

        // This action comes from our ongoing notification. The user requested to stop updates.
        if (intent?.action == ACTION_STOP_UPDATES) {
            locationRepository?.stopLocationUpdates()
        }

        // Startup tasks only happen once.
        if (!started) {
            started = true
            // Check if we should turn on location updates.
            lifecycleScope.launch {
                if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    locationRepository?.startLocationUpdates()
                }

            }
            // Update any foreground notification when we receive location updates.
            lifecycleScope.launch {
                locationRepository?.location?.collect(::showNotification)
            }
        }

        // Decide whether to remain in the background, promote to the foreground, or stop.
        manageLifetime()

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        handleBind()
        return localBinder
    }

    override fun onRebind(intent: Intent?) {
        handleBind()
    }

    private fun handleBind() {
        bindCount++
        // Start ourself. This will let us manage our lifetime separately from bound clients.
        startService(Intent(this, this::class.java))
    }

    override fun onUnbind(intent: Intent?): Boolean {
        bindCount--
        lifecycleScope.launch {
            // UI client can unbind because it went through a configuration change, in which case it
            // will be recreated and bind again shortly. Wait a few seconds, and if still not bound,
            // manage our lifetime accordingly.
            delay(UNBIND_DELAY_MILLIS)
            manageLifetime()
        }
        // Allow clients to rebind, in which case onRebind will be called.
        return true
    }

    private fun manageLifetime() {
        when {
            // We should not be in the foreground while UI clients are bound.
            isBound() -> exitForeground()

            // Location updates were started.
            locationRepository?.isReceivingLocationUpdates?.value == true -> enterForeground()

            locationRepository?.isReceivingLocationUpdates?.value == false -> enterForeground()

            // Nothing to do, so we can stop.
            else -> stopSelf()
        }
    }

    private fun exitForeground() {
        if (isForeground) {
            isForeground = false
            stopForeground(true)
        }
    }

    internal inner class LocalBinder : Binder() {
        fun getService(): RecordService = this@RecordService
    }

    private fun showNotification(location: Location?) {
        if (!isForeground) {
            return
        }

        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification(location))
    }

    private fun enterForeground() {
        if (!isForeground) {
            isForeground = true

            // Show notification with the latest location.
            showNotification(locationRepository?.location?.value)
        }
    }

    private companion object {
        const val UNBIND_DELAY_MILLIS = 2000.toLong() // 2 seconds
    }

    override fun startLocationUpdates() {
        locationRepository?.startLocationUpdates()
    }

    override fun stopLocationUpdates() {
        locationRepository?.stopLocationUpdates()
    }
}
