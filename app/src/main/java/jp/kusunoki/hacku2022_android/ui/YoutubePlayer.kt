package jp.kusunoki.hacku2022_android.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/** AndroidViewと有志のライブラリを使用したYoutube Player.
 *
 * [android-youtube-player](https://github.com/PierfrancescoSoffritti/android-youtube-player)
 *
 * 使用例：YoutubePlayer(videoId = "VIDEO_ID", modifier = ...) { second -> ... }
 **/

@Composable
fun YouTubePlayer(
    videoId: String,
    startSeconds: Float,
    modifier : Modifier = Modifier,
    onCurrentSecond: (second: Float) -> Unit = {}
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val view = YouTubePlayerView(context)
            view.addYouTubePlayerListener(
                object : AbstractYouTubePlayerListener() {
                    // Viewが作成された際に呼ばれる
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.loadVideo(videoId, startSeconds)
                    }

                    // Playerの状態が変更された時に呼ばれ、状態を取得可能(PLAYINGやPAUSED)
                    // 現時点ではそこまで必要なし？
                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {
                        super.onStateChange(youTubePlayer, state)
                    }

                    // 再生中の総秒数が取得可能
                    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                        super.onCurrentSecond(youTubePlayer, second)
                        onCurrentSecond(second)
                    }
                }
            )
            view
        }
    )
}

// 一番シンプルなYoutubePlayer
@Composable
fun YouTubePlayer(
    videoId: String,
    modifier : Modifier = Modifier,
    onCurrentSecond: (second: Float) -> Unit = {}
) {
    YouTubePlayer(
        videoId = videoId,
        startSeconds = 0f,
        modifier = modifier,
        onCurrentSecond = onCurrentSecond
    )
}