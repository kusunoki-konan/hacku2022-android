package jp.kusunoki.hacku2022_android.data.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.kusunoki.hacku2022_android.data.module.YoutubeService
import jp.kusunoki.hacku2022_android.data.model.YoutubeList
import jp.kusunoki.hacku2022_android.util.Future
import jp.kusunoki.hacku2022_android.util.apiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


interface YoutubeRepository {}
class YoutubeRepositoryImpl @Inject constructor(
    private val youtubeService: YoutubeService
): YoutubeRepository {

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

@Module
@InstallIn(SingletonComponent::class)
object YoutubeModule {
    @Singleton
    @Provides
    fun provideYoutubeRepository(
        youtubeService: YoutubeService
    ): YoutubeRepository = YoutubeRepositoryImpl(youtubeService)
}