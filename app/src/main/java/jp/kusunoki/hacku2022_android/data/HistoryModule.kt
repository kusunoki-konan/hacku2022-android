package jp.kusunoki.hacku2022_android.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, HistoryDatabase::class.java, "history_table").build()

    @Singleton
    @Provides
    fun provideDao(db: HistoryDatabase) = db.historyDao()

    @Singleton
    @Provides
    fun provideHistoryRepository(
        dao: HistoryDao
    ): HistoryRepository = HistoryRepositoryImpl(dao)
}