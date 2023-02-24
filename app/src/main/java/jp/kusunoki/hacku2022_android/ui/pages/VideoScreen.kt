package jp.kusunoki.hacku2022_android.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import jp.kusunoki.hacku2022_android.data.model.HistoryEntity
import jp.kusunoki.hacku2022_android.ui.viewmodel.HistoryViewModel
import jp.kusunoki.hacku2022_android.R
import jp.kusunoki.hacku2022_android.ui.parts.YouTubePlayer
import jp.kusunoki.hacku2022_android.ui.parts.CommentSheet
import timber.log.Timber
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
                val state = remember {
                    mutableStateOf(PlayerConstants.PlayerState.PLAYING)
                }

                YouTubePlayer(
                    videoId = videoId,
                    startSeconds = time,
                    onCurrentSecond = {
                        currentTime.value = it
                    },
                    onStateChange = {
                        state.value = it
                    },
                    onReady = {
                    }
                )
                CommentSheet(
                    videoNowTime = currentTime.value,
                    onTapContent = {
                    },
                    onReturnPlayer = {
                    }
                )
            }
        }
    }
}

val LocalYouTubePlayer = compositionLocalOf<YouTubePlayer> {
    error("No YouTubePlayer provided")
}