package com.musicradar.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MusicRadarApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
