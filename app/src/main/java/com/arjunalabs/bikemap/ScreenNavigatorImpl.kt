package com.arjunalabs.bikemap

import android.content.ContentValues
import android.content.UriMatcher
import android.net.Uri
import com.arjunalabs.bikemap.feature.home.HomeScreen
import com.arjunalabs.bikemap.feature.map.MapScreen
import com.arjunalabs.bikemap.utility.navigation.ScreenNavigator
import com.zhuinden.simplestack.Backstack

class ScreenNavigatorImpl(private val backstack: Backstack) : ScreenNavigator {

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private val authority = "bikemap"
    enum class UriMatcherResult {
        Home,
        Map
    }
    init {
        uriMatcher.addURI(authority, "home", UriMatcherResult.Home.ordinal)
        uriMatcher.addURI(authority, "map", UriMatcherResult.Map.ordinal)
    }

    override fun redirectTo(uri: Uri, contentValues: ContentValues) {
        when (uriMatcher.match(uri)) {
            UriMatcherResult.Home.ordinal -> {
                backstack.goTo(HomeScreen())
            }
            UriMatcherResult.Map.ordinal -> {
                backstack.goTo(MapScreen.parseContentValues(contentValues))
            }
        }
    }
}