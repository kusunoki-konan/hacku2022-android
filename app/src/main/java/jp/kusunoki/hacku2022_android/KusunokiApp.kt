package jp.kusunoki.hacku2022_android

import android.app.Application
import com.pierfrancescosoffritti.androidyoutubeplayer.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class KusunokiApp: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}