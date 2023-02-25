package jp.kusunoki.hacku2022_android.data.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.kusunoki.hacku2022_android.data.repository.HistoryRepository
import jp.kusunoki.hacku2022_android.data.repository.HistoryRepositoryImpl
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