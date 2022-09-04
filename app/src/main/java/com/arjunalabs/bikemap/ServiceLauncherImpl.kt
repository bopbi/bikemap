package com.arjunalabs.bikemap

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.activity.ComponentActivity
import com.arjunalabs.bikemap.feature.record.LocationUpdateService
import com.arjunalabs.bikemap.feature.record.RecordService
import com.arjunalabs.bikemap.utility.service.ServiceLauncher

class ServiceLauncherImpl(private val context: Context, private val serviceConnection: ServiceConnection) : ServiceLauncher {
    override fun bindService() {
        val serviceIntent = Intent(context, RecordService::class.java)
        context.bindService(serviceIntent, serviceConnection, ComponentActivity.BIND_AUTO_CREATE)
    }

    override fun unbindService() {
        context.unbindService(serviceConnection)
    }

    override fun startListenLocationUpdates() {
        (serviceConnection as? LocationUpdateService)?.startLocationUpdates()
    }

    override fun stopListenLocationUpdates() {
        (serviceConnection as? LocationUpdateService)?.stopLocationUpdates()
    }
}
