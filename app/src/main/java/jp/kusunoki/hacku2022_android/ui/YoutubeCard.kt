package jp.kusunoki.hacku2022_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun YoutubeCard() {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .height(250.dp)
            .width(215.dp)
            .padding(16.dp)
            .wrapContentSize(),
        backgroundColor= MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        CardContent()
    }
}
@Composable
fun CardContent() {
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.image),
            contentDescription = "サムネ画像",
            modifier = Modifier
                .height(118.dp)
                .width(215.dp),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.drawable.circle),
                contentDescription = "チャンネル画像",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(4.dp)
                    .size(32.dp)
                    .clip(CircleShape)
            )
            Text(
                "youtuber講座あいうえおかきくけこ",
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.subtitle1 ,
                maxLines = 1, // 最大1行に制限する
                overflow = TextOverflow.Ellipsis // 最大行数を超えた場合に末尾を省略する
            )
        }
        Text(
            "超人気youtuberから学ぶyoutuberになるには！あいうえおかきくけこ",
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            maxLines = 2, // 最大2行に制限する
            overflow = TextOverflow.Ellipsis // 最大行数を超えた場合に末尾を省略する
        )
    }
}