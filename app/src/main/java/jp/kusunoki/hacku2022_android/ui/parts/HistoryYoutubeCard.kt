package jp.kusunoki.hacku2022_android.ui.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jp.kusunoki.hacku2022_android.R

@Composable
fun HistoryYoutubeCard(
    title: String = "",
    channelTitle: String = "",
    thumbnailPath: String = "",
    onCardClicked: () -> Unit
) {
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .width(LocalConfiguration.current.screenWidthDp.dp)
            .clickable(onClick = onCardClicked)
            .padding(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    ) {
        Row {
            val padding = 8.dp
            val imageHeight = 112.5.dp
            val imageWidth = 150.dp

            if (thumbnailPath.isNotBlank()) {
                AsyncImage(
                    model = thumbnailPath,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.image),
                    modifier = Modifier
                        .padding(padding)
                        .height(imageHeight)
                        .width(imageWidth),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = stringResource(R.string.samune_image),
                    modifier = Modifier
                        .padding(padding)
                        .height(imageHeight)
                        .width(imageWidth),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            }

            Column {
                Text(
                    title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1, // 最大1行に制限する
                    overflow = TextOverflow.Ellipsis // 最大行数を超えた場合に末尾を省略する
                )
                Text(
                    channelTitle,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    maxLines = 1, // 最大1行に制限する
                    overflow = TextOverflow.Ellipsis // 最大行数を超えた場合に末尾を省略する
                )
                Text(
                    stringResource(R.string.when_play_mock),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    maxLines = 1, // 最大1行に制限する
                    overflow = TextOverflow.Ellipsis // 最大行数を超えた場合に末尾を省略する
                )
            }
        }
    }
}