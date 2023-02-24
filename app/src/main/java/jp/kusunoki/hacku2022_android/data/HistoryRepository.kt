package jp.kusunoki.hacku2022_android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.kusunoki.hacku2022_android.data.HistoryDao
import jp.kusunoki.hacku2022_android.data.HistoryEntity
import jp.kusunoki.hacku2022_android.util.Future
import jp.kusunoki.hacku2022_android.util.localFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface HistoryRepository{}
class HistoryRepositoryImpl @Inject constructor(private val historyDao: HistoryDao):HistoryRepository {

    fun getAllHistory(): LiveData<List<HistoryEntity>> {
        return MutableLiveData(historyDao.getAllHistory())
    }

    fun getHistoryList(): Flow<Future<List<HistoryEntity>>> = localFlow { historyDao.getAllHistory() }

    suspend fun insert(history: HistoryEntity) {
        historyDao.insert(history)
    }

    fun update(history: HistoryEntity) {
        historyDao.update(history)
    }

    suspend fun deleteAll() {
        historyDao.deleteAll()
    }
}