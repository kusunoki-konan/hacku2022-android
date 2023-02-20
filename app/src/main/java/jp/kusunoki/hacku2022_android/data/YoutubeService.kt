package jp.kusunoki.hacku2022_android.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface YoutubeService {

    companion object {
        const val MAX_RESULT_VALUE = "maxResults"
        const val MAX_RESULT = "&maxResults={maxResults}"
        const val RELATED_TO_VIDEO_ID_VALUE = "relatedToVideoId"
        const val RELATED_TO_VIDEO_ID = "&relatedToVideoId={relatedToVideoId}"
        const val QUERY_VALUE = "q"
        const val QUERY = "&q={q}"
    }

    @GET(MAX_RESULT)
    suspend fun fetchVideoList(
        @Path(MAX_RESULT_VALUE) max: Int = 5,
    ): Response<YoutubeList>

    @GET("$MAX_RESULT$RELATED_TO_VIDEO_ID")
    suspend fun fetchRelatedVideoList(
        @Path(MAX_RESULT_VALUE) max: Int = 5,
        @Path(RELATED_TO_VIDEO_ID_VALUE) videoId: String,
    ): Response<YoutubeList>

    @GET("$MAX_RESULT$QUERY")
    suspend fun fetchVideoList(
        @Path(MAX_RESULT_VALUE) max: Int = 5,
        @Path(QUERY_VALUE) query: String
    ): Response<YoutubeList>
}