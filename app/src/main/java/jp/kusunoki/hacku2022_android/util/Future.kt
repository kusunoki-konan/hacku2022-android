package jp.kusunoki.hacku2022_android.util

/** 「Coroutine Flow + Retrofit (+ Dagger Hilt) で 安全なAPIコールを実現する」より
 *
 * https://zenn.dev/chmod644/articles/fc304b7e2508de
 */

sealed class Future<out T> {
    object Proceeding : Future<Nothing>()
    data class Success<out T>(val value: T) : Future<T>()
    data class Error(val error: Throwable) : Future<Nothing>()
}