package com.example.moneytreelighttest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MTLApplication : Application() {

    companion object {
        lateinit var instance: MTLApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}