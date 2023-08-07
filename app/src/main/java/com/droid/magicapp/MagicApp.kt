package com.droid.magicapp

import android.app.Application
import com.droid.magicapp.utils.firebaseDBModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MagicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MagicApp)
            modules(firebaseDBModule)
        }
    }
}