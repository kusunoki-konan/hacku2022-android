package jp.kusunoki.hacku2022_android.data

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.kusunoki.hacku2022_android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        val API_KEY = BuildConfig.YOUTUBE_API_KEY
        val baseUrl = "https://www.googleapis.com/youtube/v3/search?key=$API_KEY&part=snippet&type=video"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideYoutubeService(retrofit: Retrofit): YoutubeService = retrofit.create(YoutubeService::class.java)
}