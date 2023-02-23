package jp.kusunoki.hacku2022_android

import android.app.Application
import androidx.room.Room

class RoomApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "todos"
        ).build()
    }
}