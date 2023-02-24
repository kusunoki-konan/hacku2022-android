package jp.kusunoki.hacku2022_android.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

inline fun <reified T : Any> localFlow(crossinline call: suspend () -> T): Flow<Future<T>> =
    flow<Future<T>> {
        val response = call()
        // 成功した場合はFuture.Successに値をラップして出力
        emit(Future.Success(value = response))
    }.catch { it: Throwable ->
        // エラーが発生した場合はFuture.Errorに例外をラップして出力
        emit(Future.Error(it))
    }.onStart {
        // 起動時にFuture.Proceedingを出力
        emit(Future.Proceeding)
    }.flowOn(Dispatchers.IO)