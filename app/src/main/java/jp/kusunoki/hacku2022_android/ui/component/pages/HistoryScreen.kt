package jp.kusunoki.hacku2022_android.ui.component.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import jp.kusunoki.hacku2022_android.HistoryYoutubeCard
import jp.kusunoki.hacku2022_android.R
import timber.log.Timber

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryScreen() {
    // TODO: 履歴画面
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