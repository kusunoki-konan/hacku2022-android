package jp.kusunoki.hacku2022_android.ui.component.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.TypeConverter
import jp.kusunoki.hacku2022_android.HistoryEntity
import jp.kusunoki.hacku2022_android.HistoryViewModel
import java.util.*
import androidx.compose.ui.res.stringResource
import jp.kusunoki.hacku2022_android.HistoryYoutubeCard
import jp.kusunoki.hacku2022_android.R
import timber.log.Timber

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryScreen(viewModel: HistoryViewModel = hiltViewModel()) {
    viewModel.refresh()
    // TODO: 履歴画面
    // データの挿入
    val historyEntity = HistoryEntity(0, "videoId", "title", "thumbnailPath", Date())
//    viewModel.insert(historyEntity)

    // データの更新
    // historyListに入っているのはListを変換してStateにして入れている
    val historyListState = viewModel.historyList.observeAsState() // 更新するデータを取得する
//    Timber.d("$historyList")
    // データの削除
//    viewModel.deleteAll()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.play_history)) }
                )
            },
            content = {
                val state = rememberScrollState()
                Column{
                    val historyFutureList = historyListState.value
                    Text("$historyFutureList")
                }
                LazyColumn{
                    items(10) {
                        HistoryYoutubeCard(onCardClicked = {
                            Timber.d("Card clicked!")
                        })
                    }
                }
            }
        )
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
