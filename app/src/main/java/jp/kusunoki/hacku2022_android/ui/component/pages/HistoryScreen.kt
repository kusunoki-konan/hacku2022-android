package jp.kusunoki.hacku2022_android.ui.component.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.TypeConverter
import jp.kusunoki.hacku2022_android.HistoryViewModel
import java.util.*


@Composable
fun HistoryScreen(viewModel: HistoryViewModel = hiltViewModel()) {
    viewModel.refresh()
    // TODO: 履歴画面
    // データの挿入
//    val historyEntity = HistoryEntity(0, "videoId", "title", "thumbnailPath", Date())
//    viewModel.insert(historyEntity)

    // データの更新
    // historyListに入っているのはListを変換してStateにして入れている
    val historyList = viewModel.historyList.observeAsState() // 更新するデータを取得する
//    Timber.d("$historyList")
    // データの削除
//    viewModel.deleteAll()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column{
            Text("${historyList.value}")
        }
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
