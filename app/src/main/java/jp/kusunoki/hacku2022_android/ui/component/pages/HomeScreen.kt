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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import jp.kusunoki.hacku2022_android.YoutubeCardList
import jp.kusunoki.hacku2022_android.ui.SearchBar

@Composable
fun HomeScreen(navController: NavController) {
    // TODO: Home画面
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column {
            val textFieldState = remember { mutableStateOf(TextFieldValue("")) }
            SearchBar(textFieldState = textFieldState) {
                // TODO safe argsで安全に値渡しをしたい
                val text = textFieldState.value.text
                if (text.isNotEmpty()) {
                    // ?,/ に関しては URL エンコーディングを適応
                val encodedUrl = text
                    .replace("/", "%2F")
                    .replace("?", "%3F")
                navController.navigate("video/$encodedUrl")
                }
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