package com.arjunalabs.bikemap

import android.content.ContentValues
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.arjunalabs.bikemap.feature.home.HomeScreen
import com.arjunalabs.bikemap.feature.record.RecordServiceConnection
import com.arjunalabs.bikemap.utility.navigation.ScreenNavigator
import com.arjunalabs.bikemap.utility.service.ServiceLauncher
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import timber.log.Timber

class MainActivity : AppCompatActivity(), SimpleStateChanger.NavigationHandler, ScreenNavigator, ServiceLauncher {
    private lateinit var fragmentStateChanger: DefaultFragmentStateChanger
    private var screenNavigator: ScreenNavigator? = null
    private var serviceLauncher: ServiceLauncher? = null

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
        serviceLauncher = ServiceLauncherImpl(this, RecordServiceConnection())
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

    override fun bindService() {
        serviceLauncher?.bindService()
    }

    override fun unbindService() {
        serviceLauncher?.unbindService()
    }

    override fun startListenLocationUpdates() {
        serviceLauncher?.startListenLocationUpdates()
    }

    override fun stopListenLocationUpdates() {
        serviceLauncher?.stopListenLocationUpdates()
    }

}
