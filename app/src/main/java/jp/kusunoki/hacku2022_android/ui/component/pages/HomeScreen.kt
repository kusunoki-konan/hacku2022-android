package jp.kusunoki.hacku2022_android.ui.component.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import jp.kusunoki.hacku2022_android.ui.SearchBar

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
        }
    }
}