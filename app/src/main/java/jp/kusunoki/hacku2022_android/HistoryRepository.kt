package jp.kusunoki.hacku2022_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class HistoryRepository(private val historyDao: HistoryDao) {

    fun getAllHistory(): LiveData<List<HistoryEntity>> {
        return MutableLiveData(historyDao.getAllHistory())
    }


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
