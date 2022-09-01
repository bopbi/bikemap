package com.arjunalabs.bikemap.utility.navigation

import android.content.ContentValues
import android.net.Uri
import android.os.Parcel

interface ScreenNavigator {

    fun redirectTo(uri: Uri, contentValues: ContentValues)
}