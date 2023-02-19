package jp.kusunoki.hacku2022_android.ui.component.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
            if(youtubeVideoId(text)!=null) {
                YoutubePlayer(videoId = "$VIDEO_ID", modifier = Modifier)
                Text("$VIDEO_ID")
            }
            if(youtubeVideoId(text)==null){
                Text("その動画は存在しません")
            }
        }
    }
}
//youtubeのVideoIdを取得する
// TODO タイムがある時にはまだ適応していない
fun youtubeVideoId(url: String): String? {
    var matchResult: MatchResult? = null
    //普通のurl、再生リストの動画のurlなど
    if(url.indexOf("youtube.com/watch?")!=-1){
        val pattern = "(?<=v=)[^&$]+".toRegex()
        matchResult = pattern.find(url)
    }
    //短縮urlの場合
    else if(url.indexOf("youtu.be/")!=-1){
        val pattern = "(?<=youtu.be/)[^?]+".toRegex()
        matchResult = pattern.find(url)
    }
    //埋め込みurlの場合
    else if(url.indexOf("youtube.com/embed/")!=-1){
        val pattern = "(?<=embed/)[^?]+".toRegex()
        matchResult = pattern.find(url)
    }
    else{
        return null
    }
    return matchResult?.value
}