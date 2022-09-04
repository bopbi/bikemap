package com.arjunalabs.bikemap.feature.record

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class RecordServiceConnection : ServiceConnection {
    var service: RecordService? = null
        private set

    override fun onServiceConnected(name: ComponentName, binder: IBinder) {
        service = (binder as RecordService.LocalBinder).getService()
    }

    override fun onServiceDisconnected(name: ComponentName) {
        // Note: this should never be called since the service is in the same process.
        service = null
    }
}
