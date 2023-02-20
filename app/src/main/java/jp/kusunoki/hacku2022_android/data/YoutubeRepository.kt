package jp.kusunoki.hacku2022_android.data

import jp.kusunoki.hacku2022_android.BuildConfig
import jp.kusunoki.hacku2022_android.util.Future
import jp.kusunoki.hacku2022_android.util.apiFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class YoutubeRepository @Inject constructor(
    private val youtubeService: YoutubeService
) {

    // ランダムに動画のリストが返ってきます
    fun getVideoList(): Flow<Future<YoutubeList>> = apiFlow { youtubeService.fetchVideoList() }

    // ランダムに指定した数のリストが返ってきます
    fun getVideoList(max: Int = 1): Flow<Future<YoutubeList>> = apiFlow {
        youtubeService.fetchVideoList(max)
    }

    // 指定したvideoIdに関連する動画のリストが返ってきます
    fun getRelatedVideoList(videoId: String): Flow<Future<YoutubeList>> = apiFlow {
        youtubeService.fetchRelatedVideoList(videoId = videoId)
    }

    // 指定した数とvideoIdに関連する動画のリストが返ってきます
    fun getRelatedVideoList(
        max: Int = 5,
        videoId: String
    ): Flow<Future<YoutubeList>> = apiFlow {
        youtubeService.fetchRelatedVideoList(max, videoId)
    }

    // クエリ入りで動画のリストが返ってきます
    fun getVideoList(
        query: String
    ): Flow<Future<YoutubeList>> = apiFlow {
        youtubeService.fetchVideoList(query = query)
    }
}