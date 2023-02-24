package jp.kusunoki.hacku2022_android.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.kusunoki.hacku2022_android.data.model.YoutubeList
import jp.kusunoki.hacku2022_android.data.repository.YoutubeRepositoryImpl
import jp.kusunoki.hacku2022_android.util.Future
import jp.kusunoki.hacku2022_android.util.youtubeVideoId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val youtubeRepository: YoutubeRepositoryImpl
): ViewModel() {
    val recommendVideoState = MutableStateFlow<Future<YoutubeList>>(Future.Proceeding)
    val newVideoState = MutableStateFlow<Future<YoutubeList>>(Future.Proceeding)
    val searchResultState = MutableStateFlow<Future<YoutubeList>>(Future.Proceeding)

    fun refresh() = viewModelScope.launch {
        youtubeRepository.getRelatedVideoList(10, "NRDko7XBD7I")
            .collectLatest {
                recommendVideoState.value = it
                Timber.d("$it")
            }
        youtubeRepository.getVideoList(10)
            .collectLatest {
                newVideoState.value = it
                Timber.d("$it")
            }
    }

    fun search(query: String) = viewModelScope.launch {
        var formatQuery = ""
        if (query.startsWith("https")) {
            formatQuery = query.youtubeVideoId()
        }
        if(formatQuery.isNotBlank()) {
            Timber.d(formatQuery)
            youtubeRepository.getVideoList(formatQuery)
                .collectLatest {
                    searchResultState.value = it
                    Timber.d("$it")
                }
        } else {
            Timber.d(query)
            youtubeRepository.getVideoList(query)
                .collectLatest {
                    searchResultState.value = it
                    Timber.d("$it")
                }
        }
    }
}