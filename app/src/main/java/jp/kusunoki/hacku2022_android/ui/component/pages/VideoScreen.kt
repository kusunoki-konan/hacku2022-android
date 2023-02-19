package jp.kusunoki.hacku2022_android.ui.component.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.kusunoki.hacku2022_android.ui.YoutubePlayer

@Composable
fun VideoScreen(text: String) {
    // TODO: 動画再生画面
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(){
            val VIDEO_ID = youtubeVideoId(text)
            YoutubePlayer(videoId = "$VIDEO_ID", modifier = Modifier)
            // TODO 日本語入力した時のものを考える
        }
    }
}
//youtubeのVideoIdを取得する
// TODO 短縮URLなど、その他の場合でもVIDEO_IDを取得したい
fun youtubeVideoId(url: String): String? {
    val pattern = "(?<=v=)[^&$]+".toRegex()
    val matchResult = pattern.find(url)
    return matchResult?.value
}