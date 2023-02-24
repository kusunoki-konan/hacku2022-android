package jp.kusunoki.hacku2022_android.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import jp.kusunoki.hacku2022_android.LocalNavController
import jp.kusunoki.hacku2022_android.R
import jp.kusunoki.hacku2022_android.Screen
import jp.kusunoki.hacku2022_android.data.model.YoutubeList
import jp.kusunoki.hacku2022_android.ui.parts.*
import jp.kusunoki.hacku2022_android.ui.viewmodel.HomeViewModel
import jp.kusunoki.hacku2022_android.util.Future
import timber.log.Timber

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val navController = LocalNavController.current
    val recommendVideoList = viewModel.recommendVideoState.collectAsState()
    val newVideoList = viewModel.newVideoState.collectAsState()
    val searchResultVideoList = viewModel.searchResultState.collectAsState()

//    viewModel.refresh()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column {
            val textFieldState = remember { mutableStateOf(TextFieldValue("")) }
            val isSearchResultShow = remember { mutableStateOf(false) }
            
            SearchBar(textFieldState = textFieldState) {
                val videoUrl = textFieldState.value.text
                if (videoUrl.isNotEmpty()) {
                    isSearchResultShow.value = true
//                    ?,/ に関しては URL エンコーディングを適応
//                    val encodedUrl = videoUrl
//                        .replace("/", "%2F")
//                        .replace("?", "%3F")
//                    navController.navigate("${Screen.Video.route}/$encodedUrl")

                    navController.navigate("${Screen.Video.route}/NRDko7XBD7I")
//                    viewModel.search(videoUrl)
                }
            }
            if (isSearchResultShow.value) {
                when(searchResultVideoList.value) {
                    is Future.Proceeding -> {

                    }
                    is Future.Success -> {
                        val list = (searchResultVideoList.value as Future.Success<YoutubeList>).value.items
                        LazyColumn {
                            items(list) {item ->
                                val title = item.snippet.title
                                val channelTitle = item.snippet.channelTitle
                                val thumbnail = item.snippet.thumbnails.medium.url
                                val videoId = item.id.videoId

                                LandscapeYoutubeCard(
                                    title = title,
                                    channelTitle = channelTitle,
                                    thumbnailPath = thumbnail,
                                    onCardClicked = {
                                        navController.navigate("${Screen.Video.route}/$videoId")
                                    }
                                )
                            }
                        }
                    }
                    is Future.Error -> {

                    }
                }
            } else {
                HomeContent(
                    recommendVideoList = recommendVideoList.value,
                    newVideoList = newVideoList.value
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    recommendVideoList: Future<YoutubeList>,
    newVideoList: Future<YoutubeList>
) {
    val navController = LocalNavController.current
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
                modifier = Modifier
                    .padding(all = 8.dp)
                    .clickable {
                        Firebase.auth.signOut()
                        navController.navigate(Screen.Login.route)
                    }
            )
            YouTubeCardRow(recommendVideoList)

            ListSpacer(modifier = Modifier.padding(8.dp, 0.dp))
            Text(
                stringResource(R.string.new_video),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(all = 8.dp)
            )
            YouTubeCardRow(newVideoList)

            ListSpacer(modifier = Modifier.padding(8.dp, 0.dp))
            Text(
                stringResource(R.string.popular_video),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(all = 8.dp)
            )
            YouTubeCardRow()
        }
    }
}