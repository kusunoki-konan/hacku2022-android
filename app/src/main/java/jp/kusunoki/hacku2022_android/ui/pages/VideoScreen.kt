package jp.kusunoki.hacku2022_android.ui.pages

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import jp.kusunoki.hacku2022_android.R
import jp.kusunoki.hacku2022_android.data.model.Comment
import jp.kusunoki.hacku2022_android.ui.parts.YouTubePlayer
import jp.kusunoki.hacku2022_android.ui.parts.CommentSheetContent
import jp.kusunoki.hacku2022_android.ui.parts.convertToTimeString
import jp.kusunoki.hacku2022_android.ui.viewmodel.VideoViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@Composable
fun VideoScreen(videoId: String = "", time: Float = 0f, viewModel: VideoViewModel = hiltViewModel()) {
    val input = viewModel.comment
    val commentState = viewModel.commentState.collectAsState()
    input.value = input.value.copy(videoId = videoId)

    if(videoId.isBlank()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Text(stringResource(R.string.no_video))
        }
    } else {
        // データの挿入
        viewModel.historySave(videoId)
        viewModel.getComment(videoId)

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column {
                val currentTime = remember { mutableStateOf(0f) }
                val state = remember {
                    mutableStateOf(PlayerConstants.PlayerState.PLAYING)
                }

                YouTubePlayer(
                    videoId = videoId,
                    startSeconds = time,
                    onCurrentSecond = {
                        currentTime.value = it
                    },
                    onStateChange = {
                        state.value = it
                    },
                    onReady = {
                    }
                )
                CommentSheet(
                    inputComment = input,
                    videoNowTime = currentTime.value,
                    onTapContent = {
                    },
                    onReturnPlayer = {
                    },
                    onSubmit = {
                        val e = viewModel.addComment()
                        Timber.d(e)
                        if (e == null) {
                            input.value = Comment(videoId = videoId)
                        }
                    }
                ) {
                    if(commentState.value.isNotEmpty()) {
                        CommentList(
                            commentState.value,
                            agree = {

                            },
                            upvote = {

                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommentList(
    comments: List<Comment>,
    agree: (comment: Comment) -> Unit = {},
    upvote: (comment: Comment) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        items(comments) { comment ->
            Spacer(modifier = Modifier.padding(0.dp, 4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CommentContent(
                    modifier = Modifier.weight(5f).padding(4.dp),
                    comment = comment.comment.replace("\\n", "\n"),
                    agree = {
                        agree(comment)
                    },
                    upvote = {
                        upvote(comment)
                    }
                )

                TimeLine(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    playTimeStr = convertToTimeString(comment.playTime.toFloat())
                )
            }
            Spacer(modifier = Modifier.padding(0.dp, 4.dp))
        }
    }
}

@Composable
fun CommentContent(
    modifier: Modifier = Modifier,
    comment: String,
    agree: () -> Unit = {},
    upvote: () -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        Text(comment)
        Spacer(modifier = Modifier.padding(0.dp, 4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                agree()
            }) {
                Text(
                    text = "私も知りたい",
                    color = Color.Black
                )
            }
            Button(onClick = {
                upvote()
            }) {
                Text(
                    text = "役に立った",
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun TimeLine(
    modifier: Modifier = Modifier,
    playTimeStr: String
) {
    Column(
        modifier = modifier
    ) {
        Text(playTimeStr)
        VerticalDivider()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentSheet(
    inputComment: MutableState<Comment>,
    videoNowTime: Float,
    onTapContent: () -> Unit = {},
    onReturnPlayer: () -> Unit = {},
    onSubmit: () -> Unit = {},
    commentList: @Composable () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch {
            sheetState.hide()
            onReturnPlayer()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            CommentSheetContent(
                inputComment = inputComment,
                videoNowTime = videoNowTime,
                onSheetHide = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onReturnPlayer()
                    }
                },
                onSubmit = onSubmit
            )
        },
        sheetShape = RoundedCornerShape(5, 5, 0, 0),
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (sheetState.isVisible) {
                            sheetState.hide()
                        } else {
                            onTapContent()
                            sheetState.show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = Color.Gray,
                    disabledContentColor = Color.LightGray
                )
            ) {
                Text(stringResource(R.string.question_add))
            }
            Divider()
            commentList()
        }
    }
}

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray,
    startIndent: Dp = 0.dp,
) {
    val indentMod = if (startIndent.value != 0f) {
        Modifier.padding(start = startIndent)
    } else {
        Modifier
    }
    val thickness = 1.dp
    Box(
        modifier
            .then(indentMod)
            .fillMaxHeight()
            .width(thickness)
            .background(color = color)
    )
}