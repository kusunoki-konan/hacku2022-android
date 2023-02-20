package jp.kusunoki.hacku2022_android.data

import jp.kusunoki.hacku2022_android.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YoutubeService {

    companion object {
        private const val API_KEY = BuildConfig.YOUTUBE_API_KEY
        const val DEFAULT_PARAM = "search?key=$API_KEY&part=snippet&type=video"
    }

    @GET(DEFAULT_PARAM)
    suspend fun fetchVideoList(
        @Query("maxResults") max: Int = 5,
    ): Response<YoutubeList>

    @GET(DEFAULT_PARAM)
    suspend fun fetchRelatedVideoList(
        @Query("maxResults") max: Int = 5,
        @Query("relatedToVideoId") videoId: String,
    ): Response<YoutubeList>

    @GET(DEFAULT_PARAM)
    suspend fun fetchVideoList(
        @Query("maxResults") max: Int = 5,
        @Query("q") query: String
    ): Response<YoutubeList>
}