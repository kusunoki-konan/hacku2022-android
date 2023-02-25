package jp.kusunoki.hacku2022_android.ui.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.kusunoki.hacku2022_android.data.model.Comment
import jp.kusunoki.hacku2022_android.data.model.HistoryEntity
import jp.kusunoki.hacku2022_android.data.module.FireStorageServiceInterface
import jp.kusunoki.hacku2022_android.data.module.FireStoreService
import jp.kusunoki.hacku2022_android.data.module.YoutubeService
import jp.kusunoki.hacku2022_android.data.repository.HistoryRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val historyRepo: HistoryRepositoryImpl,
    private val youtubeService: YoutubeService,
    private val fireStoreService: FireStoreService,
    private val fireStorageService: FireStorageServiceInterface
): ViewModel() {
    val comment = mutableStateOf(Comment())
    private val collection = fireStoreService.getCollection()

    private var _imageState = MutableStateFlow(ImageState())
    val imageState get() = _imageState.asStateFlow()

    private var _commentState = MutableStateFlow<List<Comment>>(listOf())
    val commentState get() = _commentState.asStateFlow()

    fun addComment(): Exception? {
        var e: Exception? = null
        if (_imageState.value.data != null) {
            val baos = ByteArrayOutputStream()
            _imageState.value.data!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            fireStorageService.uploadImage(
                data,
                onCompleteListener = {
                    if (it.isSuccessful) {
                        comment.value = comment.value.copy(imagePath = it.result.toString())
                        fireStoreService.addComment(
                            comment = comment.value,
                            onSuccessListener = {
                                Timber.d("Add Comment Success")
                            },
                            onFailureListener = { exc ->
                                e = exc
                            }
                        )
                    }
                },
                onFailureListener = {
                    e = it
                }
            )
        } else {
            fireStoreService.addComment(
                comment = comment.value,
                onSuccessListener = {
                    Timber.d("Add Comment Success")
                },
                onFailureListener = {
                    e = it
                }
            )
        }
        return e
    }

    fun getComment(videoId: String) {
        collection.whereEqualTo("videoId", videoId).get()
            .addOnSuccessListener { documents ->
                val comments = documents.map {
                    val data = it.data
                    Comment(
                        comment = data["comment"] as String,
                        imagePath = data["imagePath"] as String,
                        playTime = (data["playTime"] as Long).toInt(),
                        videoId = videoId
                    )
                }.sortedBy {
                    it.playTime
                }
                _commentState.value = comments
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents: ", exception)
            }
    }

    fun setImage(bitmap: Bitmap) {
        try {
            _imageState.value = _imageState.value.copy(data = bitmap)
        } catch (e: Exception) {
            _imageState.value = _imageState.value.copy(error = e)
        }
    }

    fun deleteImage() {
        try {
            _imageState.value = _imageState.value.copy(data = null)
        } catch (e: Exception) {
            _imageState.value = _imageState.value.copy(error = e)
        }
    }

    fun historySave(videoId: String) {
//        viewModelScope.launch {
//            val response = youtubeService.fetchVideoList(1, videoId)
//            if (!response.isSuccessful) return@launch
//
//            val item = response.body()!!.items.first()
//            val title = item.snippet.title
//            val thumbnail = item.snippet.thumbnails.medium.url
//
//            val history = HistoryEntity(0, videoId, title, thumbnail, Date())
//            viewModelScope.launch {
//                historyRepo.insert(history)
//            }
//        }
    }

    data class ImageState(
        val data: Bitmap? = null,
        val error: Exception? = null
    )


}