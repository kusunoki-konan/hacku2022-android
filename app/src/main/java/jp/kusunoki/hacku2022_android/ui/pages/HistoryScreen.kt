package jp.kusunoki.hacku2022_android.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.TypeConverter
import jp.kusunoki.hacku2022_android.LocalNavController
import jp.kusunoki.hacku2022_android.data.model.HistoryEntity
import jp.kusunoki.hacku2022_android.ui.viewmodel.HistoryViewModel
import jp.kusunoki.hacku2022_android.ui.parts.HistoryYoutubeCard
import jp.kusunoki.hacku2022_android.R
import jp.kusunoki.hacku2022_android.Screen
import jp.kusunoki.hacku2022_android.util.Future
import timber.log.Timber
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryScreen(viewModel: HistoryViewModel = hiltViewModel()) {
    viewModel.refresh()

    // データの更新
    // historyListに入っているのはListを変換してStateにして入れている
    val historyState = viewModel.historyState.collectAsState()
    val navController = LocalNavController.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.play_history),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    backgroundColor = Color.White
                )
            },
            content = {
                when(historyState.value) {
                    is Future.Proceeding -> {

                    }
                    is Future.Success -> {
//                        val historyList = (historyState.value as Future.Success<List<HistoryEntity>>).value
                        val historyList = (1..6).map {
                            HistoryEntity(
                                it,
                                "NRDko7XBD7I",
                                "Jetpack Compose for Webを触ってみる [Kotlin]",
                                "https://i.ytimg.com/vi/NRDko7XBD7I/mqdefault.jpg",
                                Date()
                            )
                        }

                        LazyColumn{
                            items(historyList) {history ->
                                HistoryYoutubeCard(
                                    title = history.title,
                                    thumbnailPath = history.thumbnailPath,
                                    date = history.watchDate,
                                    onCardClicked = {
                                        navController.navigate("${Screen.Video.route}/${history.videoId}")
                                    }
                                )
                            }
                        }
                    }
                    is Future.Error -> {
                        val openDialog = remember { mutableStateOf(true)  }
                        val error = (historyState.value as Future.Error).error

                        if (openDialog.value) {
                            AlertDialog(
                                onDismissRequest = { openDialog.value = false },
                                title = {
                                    Text(stringResource(id = R.string.history_error_dialog_title))
                                },
                                text = {
                                    Text("${error.message}")
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            openDialog.value = false
                                        }) {
                                        Text(stringResource(id = R.string.history_error_dialog_confirm))
                                    }
                                },
                                dismissButton = {
                                    Button(
                                        onClick = {
                                            openDialog.value = false
                                        }) {
                                        Text(stringResource(id = R.string.history_error_dialog_dismiss))
                                    }
                                }
                            )
                        }

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
