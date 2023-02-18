package jp.kusunoki.hacku2022_android.ui.component.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.kusunoki.hacku2022_android.Greeting
import jp.kusunoki.hacku2022_android.LocalNavController
import jp.kusunoki.hacku2022_android.Screen

@Composable
fun HomeScreen() {
    // TODO: Home画面
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Greeting(name = "Home")
    }
}