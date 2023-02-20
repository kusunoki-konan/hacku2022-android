package jp.kusunoki.hacku2022_android.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response

/** 「Coroutine Flow + Retrofit (+ Dagger Hilt) で 安全なAPIコールを実現する」より
 *
 * https://zenn.dev/chmod644/articles/fc304b7e2508de
 */

inline fun <reified T : Any> apiFlow(crossinline call: suspend () -> Response<T>): Flow<Future<T>> =
    flow<Future<T>> {
        val response = call()
        // 成功した場合は`Future.Success`に値をラップして出力
        if (response.isSuccessful) emit(Future.Success(value = response.body()!!))
        else throw HttpException(response)
    }.catch { it: Throwable ->
        // エラーが発生した場合は`Future.Error`に例外をラップして出力
        emit(Future.Error(it))
    }.onStart {
        // 起動時に`Future.Proceeding`を出力
        emit(Future.Proceeding)
    }.flowOn(Dispatchers.IO)

//inline fun <reified T : Any?> apiNullableFlow(crossinline call: suspend () -> Response<T?>): Flow<Future<T?>> =
//    flow {
//        val response = call()
//        if (!response.isSuccessful) throw HttpException(response)
//        emit(Future.Success(value = response.body()))
//    }.catch { it: Throwable ->
//        emit(Future.Error(it))
//    }.onStart {
//        emit(Future.Proceeding)
//    }.flowOn(Dispatchers.IO)