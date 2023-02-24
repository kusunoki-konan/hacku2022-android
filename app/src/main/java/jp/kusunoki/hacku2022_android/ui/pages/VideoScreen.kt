package jp.kusunoki.hacku2022_android.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import jp.kusunoki.hacku2022_android.data.HistoryEntity
import jp.kusunoki.hacku2022_android.ui.viewmodel.HistoryViewModel
import jp.kusunoki.hacku2022_android.R
import jp.kusunoki.hacku2022_android.ui.parts.YouTubePlayer
import jp.kusunoki.hacku2022_android.ui.parts.CommentSheet
import java.util.*

@Composable
fun VideoScreen(videoId: String = "", time: Float = 0f, viewModel: HistoryViewModel = hiltViewModel()) {
    if(videoId.isBlank()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Text(stringResource(R.string.no_video))
        }
    } else {
        // データの挿入
        val historyEntity = HistoryEntity(0, videoId, "title", "thumbnailPath", Date())
        viewModel.insert(historyEntity)

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column {
                val currentTime = remember { mutableStateOf(0f) }

                YouTubePlayer(
                    videoId = videoId,
                    startSeconds = time
                ) {
                    currentTime.value = it
                }
                CommentSheet(videoNowTime = currentTime.value)
            }
        }
    }
}