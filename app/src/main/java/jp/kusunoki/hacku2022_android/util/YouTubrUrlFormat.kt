package jp.kusunoki.hacku2022_android.util


//youtubeのVideoIdを取得する
fun String.youtubeVideoId(): String {
    val matchResult = when {
        //普通のurl、再生リストの動画のurlなど
        this.indexOf("youtube.com/watch?") != -1 -> {
            val pattern = "(?<=v=)[^&$]+".toRegex()
            pattern.find(this)
        }
        //短縮urlの場合
        this.indexOf("youtu.be/") != -1 -> {
            val pattern = "(?<=youtu.be/)[^?]+".toRegex()
            pattern.find(this)
        }
        //埋め込みurlの場合
        this.indexOf("youtube.com/embed/") != -1 -> {
            val pattern = "(?<=embed/)[^?]+".toRegex()
            pattern.find(this)
        }
        //ライブのurlのとき
        this.indexOf("youtube.com/live/") != -1 -> {
            val pattern = "(?<=live/)[^?]+".toRegex()
            pattern.find(this)
        }
        else -> { null }
    }
    return matchResult?.value ?: this
}

fun String.youtubeTime(): Float {
    val matchResult = when {
        //普通のurl、再生リストの動画のurlなど
        this.indexOf("youtube.com/watch?") != -1 -> {
            val pattern = "(?<=t=)\\d+".toRegex()
            pattern.find(this)
        }
        //短縮urlの場合
        this.indexOf("youtu.be/") != -1 -> {
            val pattern = "(?<=t=)\\d+".toRegex()
            pattern.find(this)
        }
        //埋め込みurlの場合
        this.indexOf("youtube.com/embed/") != -1 -> {
            val pattern = "(?<=start=)\\d+".toRegex()
            pattern.find(this)
        }
        //ライブのurlのとき
        this.indexOf("youtube.com/live/") != -1 -> {
            val pattern = "(?<=t=)\\d+".toRegex()
            pattern.find(this)
        }
        else -> {
            null
        }
    }
    return matchResult?.value?.toFloat() ?: 0f
}