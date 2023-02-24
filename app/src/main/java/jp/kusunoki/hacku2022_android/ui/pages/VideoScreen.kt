package jp.kusunoki.hacku2022_android.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import jp.kusunoki.hacku2022_android.HistoryEntity
import jp.kusunoki.hacku2022_android.HistoryViewModel
import jp.kusunoki.hacku2022_android.R
import jp.kusunoki.hacku2022_android.ui.parts.YouTubePlayer
import jp.kusunoki.hacku2022_android.ui.parts.CommentSheet
import java.util.*

@Composable
fun VideoScreen(text: String = "",viewModel: HistoryViewModel = hiltViewModel()) {
    // TODO: 動画再生画面
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            val videoId = youtubeVideoId(text)
            val videoTime = youtubeTime(text)
            if (videoId != null) {
                if (videoTime == null) {
                    YouTubePlayer(videoId = "$videoId")
                } else if (videoTime != 0.toFloat()) {
                    YouTubePlayer(
                        videoId = "$videoId",
                        startSeconds = videoTime
                    )
                    viewModel.refresh()
                    // TODO: 履歴画面
                }
                // データの挿入
                val historyEntity = HistoryEntity(0, "$videoId", "title", "thumbnailPath", Date())
                viewModel.insert(historyEntity)
                CommentSheet(videoNowTime = 100.toFloat())//とりあえず仮で秒数を入れています。
            }
            if (youtubeVideoId(text) == null) {
                Text(stringResource(R.string.no_video))
            }

        }
    }
}

//youtubeのVideoIdを取得する
fun youtubeVideoId(url: String): String? {
    val matchResult = when {
        //普通のurl、再生リストの動画のurlなど
        url.indexOf("youtube.com/watch?") != -1 -> {
            val pattern = "(?<=v=)[^&$]+".toRegex()
            pattern.find(url)
        }
        //短縮urlの場合
        url.indexOf("youtu.be/") != -1 -> {
            val pattern = "(?<=youtu.be/)[^?]+".toRegex()
            pattern.find(url)
        }
        //埋め込みurlの場合
        url.indexOf("youtube.com/embed/") != -1 -> {
            val pattern = "(?<=embed/)[^?]+".toRegex()
            pattern.find(url)
        }
        //ライブのurlのとき
        url.indexOf("youtube.com/live/") != -1 -> {
            val pattern = "(?<=live/)[^?]+".toRegex()
            pattern.find(url)
        }
        else -> {
            return null
        }
    }
    return matchResult?.value
}

fun youtubeTime(url: String): Float? {
    val matchResult = when {
        //普通のurl、再生リストの動画のurlなど
        url.indexOf("youtube.com/watch?") != -1 -> {
            val pattern = "(?<=t=)\\d+".toRegex()
            pattern.find(url)
        }
        //短縮urlの場合
        url.indexOf("youtu.be/") != -1 -> {
            val pattern = "(?<=t=)\\d+".toRegex()
            pattern.find(url)
        }
        //埋め込みurlの場合
        url.indexOf("youtube.com/embed/") != -1 -> {
            val pattern = "(?<=start=)\\d+".toRegex()
            pattern.find(url)
        }
        //ライブのurlのとき
        url.indexOf("youtube.com/live/") != -1 -> {
            val pattern = "(?<=t=)\\d+".toRegex()
            pattern.find(url)
        }
        else -> {
            return 0.toFloat()
        }
    }
    return matchResult?.value?.toFloat()
}