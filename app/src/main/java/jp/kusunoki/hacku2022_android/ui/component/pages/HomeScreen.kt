package jp.kusunoki.hacku2022_android.ui.component.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import jp.kusunoki.hacku2022_android.ui.SearchBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.kusunoki.hacku2022_android.YoutubeCardList
@Composable
fun HomeScreen() {
    // TODO: Home画面
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column {
            val textFieldState = remember { mutableStateOf(TextFieldValue("")) }
            SearchBar(textFieldState = textFieldState) {
                // TODO 検索バーに入力された文字でYoutubeのAPIを叩く
            }
            val state = rememberScrollState()
            LaunchedEffect(Unit) {
                state.animateScrollTo(0)
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(state)
                ) {
                    Text(
                        "おすすめの講座",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(all = 8.dp)
                    )
                    YoutubeCardList()
                    Text(
                        "新着の講座",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(all = 8.dp)
                    )
                    YoutubeCardList()
                    Text(
                        "人気の講座",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(all = 8.dp)
                    )
                    YoutubeCardList()
                }
            }
        }
    }
}