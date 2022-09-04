package com.arjunalabs.bikemap.utility.service

interface ServiceLauncher {

    fun bindService()

    fun unbindService()

    fun startListenLocationUpdates()

    fun stopListenLocationUpdates()
}
