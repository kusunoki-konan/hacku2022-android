package jp.kusunoki.hacku2022_android

import android.app.Application
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class KusunokiApp: Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "todos"
        ).build()
    }
}