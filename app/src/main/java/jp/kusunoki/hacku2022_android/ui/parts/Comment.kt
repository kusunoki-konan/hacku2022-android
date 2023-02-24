package jp.kusunoki.hacku2022_android.ui.parts

import android.graphics.Color.parseColor
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.kusunoki.hacku2022_android.R
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentSheet(videoNowTime: Float) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            CommentSheetContent(
                videoNowTime = videoNowTime,
                onSheetHide = {
                    coroutineScope.launch { sheetState.hide() }
                }
            )
        },
        sheetShape = RoundedCornerShape(5, 5, 0, 0),
        modifier = Modifier.fillMaxSize(),
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.circle),
                contentDescription = stringResource(R.string.channel_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                        else sheetState.show()
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
        }
    }
}

@Composable
fun CommentSheetContent(
    videoNowTime: Float,
    onSheetHide: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        val focusManager = LocalFocusManager.current
        val text = remember { mutableStateOf(TextFieldValue()) }
        val textHeight = 18
        val lineCount = 5

        Button(
            onClick = { onSheetHide() },
            modifier = Modifier
                .height(5.dp)
                .width(48.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.LightGray
            ),
            content = { }
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                convertToTimeString(videoNowTime),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .clickable { Timber.d("Clicked") }
                    .padding(end = 8.dp, start = 8.dp),
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
            Text(
                stringResource(R.string.to_question_comment),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(end = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { onSheetHide() },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close",
                    Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it },
            placeholder = { Text(stringResource(R.string.question_add)) },
            textStyle = TextStyle(
                lineHeight = 18.sp,
                color = Color.Black,
                fontSize = 18.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { (textHeight * (lineCount + 2)).sp.toDp() }) // 5行分:18×7
            ,
            singleLine = false,
            maxLines = 5,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.LightGray,//フォーカスされたときの下線
                unfocusedBorderColor = Color.LightGray,//フォーカスされていないときの下線
                cursorColor = Color.Black,//カーソル
                backgroundColor = Color.White,//背景
            ),
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            }
        )

        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ImageAttachButton(onClick = {
                Timber.d("ImageAttach")
            })

            VideoClipButton(onClick = {
                Timber.d("VideoClip")
            })
        }

        Spacer(modifier = Modifier.height(250.dp))

        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CancelButton(onClick = {
                Timber.d("Cancel")
            })
            SendButton(onClick = {
                Timber.d("Send")
            })
        }
    }
}

@Composable
fun ImageAttachButton(
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(36.dp)
            .width(180.dp)
            .padding(end = 8.dp),// 追加
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.LightGray,
            contentColor = Color.Gray,
            disabledContentColor = Color.LightGray
        ),
    ) {
        Text(stringResource(R.string.image_attach))
    }
}

@Composable
fun VideoClipButton(
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(36.dp)
            .width(180.dp)
            .padding(start = 8.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.LightGray,
            contentColor = Color.Gray,
            disabledContentColor = Color.LightGray
        ),
    ) {
        Text(stringResource(R.string.video_clip_attach))
    }
}

@Composable
fun CancelButton(
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .width(160.dp)
            .padding(end = 8.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.LightGray,
            contentColor = Color.Black,
            disabledContentColor = Color.LightGray
        ),
    ) {
        Text(stringResource(R.string.cancel))
    }
}

@Composable
fun SendButton(
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .width(160.dp)
            .padding(start = 8.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color(parseColor("#FFC34E")),
            contentColor = Color.Black,
            disabledContentColor = Color(parseColor("#FFC34E"))
        ),
    ) {
        Text(stringResource(R.string.send))
    }
}

fun convertToTimeString(sumSeconds: Float): String {
    val hours = (sumSeconds / 3600).toInt()
    val minutes = ((sumSeconds / 60) % 60).toInt()
    val secs = (sumSeconds % 60).toInt()
    return if (hours > 0) {
        "%02d:%02d:%02d".format(hours, minutes, secs)
    } else {
        "%02d:%02d".format(minutes, secs)
    }
}

