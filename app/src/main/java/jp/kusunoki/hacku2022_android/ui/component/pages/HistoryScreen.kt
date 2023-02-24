package jp.kusunoki.hacku2022_android.ui.component.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.TypeConverter
import jp.kusunoki.hacku2022_android.Greeting
import jp.kusunoki.hacku2022_android.HistoryEntity
import jp.kusunoki.hacku2022_android.HistoryViewModel
import java.util.*

@Composable
fun HistoryScreen() {
    val historyViewModel: HistoryViewModel = viewModel()
    // TODO: 履歴画面
    // データの挿入
    val history1 = HistoryEntity(0, "videoId", "title", "thumbnailPath", Date())
    historyViewModel.insert(history1)

    // データの更新
    val history2 = historyViewModel.history.value?.get(0) // 更新するデータを取得する
    history2?.title = "updated title"
    history2?.let { historyViewModel.update(it) }
    // データの削除
    historyViewModel.deleteAll()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Greeting(name = "History")
    }
}
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
