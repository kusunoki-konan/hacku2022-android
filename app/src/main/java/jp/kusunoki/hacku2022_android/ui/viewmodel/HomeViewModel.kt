package jp.kusunoki.hacku2022_android.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.kusunoki.hacku2022_android.data.YoutubeList
import jp.kusunoki.hacku2022_android.data.YoutubeRepositoryImpl
import jp.kusunoki.hacku2022_android.util.Future
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val youtubeRepository: YoutubeRepositoryImpl
): ViewModel() {
    val youtubeLiveData = MutableLiveData<Future<YoutubeList>>(Future.Proceeding)

    fun refresh() = viewModelScope.launch {
        youtubeRepository.getVideoList(5)
            .collectLatest {
                youtubeLiveData.value = it
                Timber.d("$it")
            }
    }
}