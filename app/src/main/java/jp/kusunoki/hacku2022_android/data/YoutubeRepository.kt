package jp.kusunoki.hacku2022_android.data

import jp.kusunoki.hacku2022_android.BuildConfig

class YoutubeRepository {
    private val ytApiKey = BuildConfig.YOUTUBE_API_KEY
    private val existApiKey = ytApiKey.isNotBlank()


}