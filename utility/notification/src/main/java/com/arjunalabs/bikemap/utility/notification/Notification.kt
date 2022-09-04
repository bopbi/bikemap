package com.arjunalabs.bikemap.utility.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.core.app.NotificationCompat

const val NOTIFICATION_ID = 1
const val NOTIFICATION_CHANNEL_ID = "LocationUpdates"
const val ACTION_STOP_UPDATES = "com.arjunalabs.bikemap.notification.ACTION_STOP_UPDATES"

fun Context.buildNotification(location: Location?): Notification {

    // Tapping the notification opens the app.
    val pendingIntent = PendingIntent.getActivity(
        this,
        0,
        packageManager.getLaunchIntentForPackage(this.packageName),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    // Include an action to stop location updates without going through the app UI.
    val stopIntent = PendingIntent.getService(
        this,
        0,
        Intent(this, this::class.java).setAction(ACTION_STOP_UPDATES),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val contentText = if (location != null) {
        "Lat: ${location.latitude} , Lon:  ${location.longitude}"
    } else {
        "Waiting for Location"
    }

    return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        .setContentTitle("Bikemap Location Update")
        .setContentText(contentText)
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.ic_location)
        .addAction(R.drawable.ic_stop, "Stop", stopIntent)
        .setOngoing(true)
        .setCategory(NotificationCompat.CATEGORY_SERVICE)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
        .build()
}

fun Context.createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "location service",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(notificationChannel)
    }
}
