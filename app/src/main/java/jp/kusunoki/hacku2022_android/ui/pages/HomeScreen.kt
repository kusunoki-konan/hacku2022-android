package jp.kusunoki.hacku2022_android.ui.pages

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.kusunoki.hacku2022_android.LocalNavController
import jp.kusunoki.hacku2022_android.R
import jp.kusunoki.hacku2022_android.Screen
import jp.kusunoki.hacku2022_android.ui.parts.YoutubeCardList
import jp.kusunoki.hacku2022_android.ui.parts.SearchBar
import jp.kusunoki.hacku2022_android.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    // TODO: Home画面
    val navController = LocalNavController.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column {
            val textFieldState = remember { mutableStateOf(TextFieldValue("")) }
            SearchBar(textFieldState = textFieldState) {
                val videoUrl = textFieldState.value.text
                if (videoUrl.isNotEmpty()) {
                    // ?,/ に関しては URL エンコーディングを適応
                    val encodedUrl = videoUrl
                        .replace("/", "%2F")
                        .replace("?", "%3F")
                    navController.navigate("${Screen.Video.route}/$encodedUrl")
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
                        stringResource(R.string.recommend_video),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(all = 8.dp)
                    )
                    YoutubeCardList()
                    Text(
                        stringResource(R.string.new_video),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(all = 8.dp)
                    )
                    YoutubeCardList()
                    Text(
                        stringResource(R.string.popular_video),
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