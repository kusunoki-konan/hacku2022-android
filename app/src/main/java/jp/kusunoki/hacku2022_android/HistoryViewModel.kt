package jp.kusunoki.hacku2022_android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.kusunoki.hacku2022_android.util.Future
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: HistoryRepositoryImpl) : ViewModel() {
    val historyList = MutableLiveData<Future<List<HistoryEntity>>>(Future.Proceeding)
    fun refresh() = viewModelScope.launch {
        repository.getHistoryList()
            .collectLatest {
                historyList.value = it
                Timber.d("$it")
            }
    }

//    val history: LiveData<List<HistoryEntity>> = repository.getAllHistory()

    fun insert(history: HistoryEntity) {
        viewModelScope.launch {
            repository.insert(history)
        }
    }

    fun update(history: HistoryEntity) {
        repository.update(history)
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}

