package jp.kusunoki.hacku2022_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.kusunoki.hacku2022_android.data.model.HistoryEntity
import jp.kusunoki.hacku2022_android.data.repository.HistoryRepositoryImpl
import jp.kusunoki.hacku2022_android.util.Future
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: HistoryRepositoryImpl
) : ViewModel() {
    val historyState = MutableStateFlow<Future<List<HistoryEntity>>>(Future.Proceeding)
    fun refresh() = viewModelScope.launch {
        repository.getHistoryList()
            .collectLatest {
                historyState.value = it
                Timber.d("$it")
            }
    }

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

