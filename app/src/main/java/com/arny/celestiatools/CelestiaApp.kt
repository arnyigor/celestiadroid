package com.arny.celestiatools

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.facebook.stetho.Stetho
import io.realm.Realm
import io.realm.RealmConfiguration

class CelestiaApp : Application() {
    companion object {
        @JvmStatic
        lateinit var appContext: Context
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Stetho.initializeWithDefaults(this)
        Realm.init(appContext)
        val config = RealmConfiguration.Builder()
                .name("celestia.realm")
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }
}
