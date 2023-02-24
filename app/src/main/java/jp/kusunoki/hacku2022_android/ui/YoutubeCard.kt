package jp.kusunoki.hacku2022_android

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun YoutubeCardList() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(10) {
            YoutubeCard(onCardClicked = {
                Log.d("YoutubeCard", "Card clicked!")
            })
        }
    }
}
@Composable
fun YoutubeCard(onCardClicked: () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .height(250.dp)
            .width(215.dp)
            .padding(8.dp)
            .wrapContentSize()
            .clickable(onClick = onCardClicked),
        backgroundColor= MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.image),
                contentDescription = stringResource(R.string.samune_image),
                modifier = Modifier
                    .height(112.5.dp)
                    .width(200.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Image(
                    painter = painterResource(id = R.drawable.circle),
                    contentDescription = stringResource(R.string.channel_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                )
                Text(
                    stringResource(R.string.channnel_title),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.subtitle1 ,
                    maxLines = 1, // 最大1行に制限する
                    overflow = TextOverflow.Ellipsis // 最大行数を超えた場合に末尾を省略する
                )
            }
            Text(
                stringResource(R.string.video_title),
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                maxLines = 2, // 最大2行に制限する
                overflow = TextOverflow.Ellipsis // 最大行数を超えた場合に末尾を省略する
            )
        }
    }
}