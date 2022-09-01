package com.arjunalabs.bikemap

import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.arjunalabs.bikemap.feature.home.HomeScreen
import com.arjunalabs.bikemap.utility.navigation.ScreenNavigator
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import timber.log.Timber

class MainActivity : AppCompatActivity(), SimpleStateChanger.NavigationHandler, ScreenNavigator {
    private lateinit var fragmentStateChanger: DefaultFragmentStateChanger
    private var screenNavigator: ScreenNavigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setContentView(R.layout.activity_main)

        fragmentStateChanger = DefaultFragmentStateChanger(supportFragmentManager, R.id.container)

        Navigator.configure()
            .setStateChanger(SimpleStateChanger(this))
            .install(this, findViewById(R.id.container), History.of(HomeScreen()))

        screenNavigator = ScreenNavigatorImpl(backstack)
    }

    override fun onBackPressed() {
        if (!Navigator.onBackPressed(this)) {
            super.onBackPressed()
        }
    }

    override fun onNavigationEvent(stateChange: StateChange) {
        fragmentStateChanger.handleStateChange(stateChange)
    }

    override fun redirectTo(uri: Uri, contentValues: ContentValues) {
        screenNavigator?.redirectTo(uri, contentValues)
    }

}