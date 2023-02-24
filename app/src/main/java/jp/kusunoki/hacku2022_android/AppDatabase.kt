package jp.kusunoki.hacku2022_android

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.kusunoki.hacku2022_android.ui.pages.Converters

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}