package jp.kusunoki.hacku2022_android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KusunokiApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}