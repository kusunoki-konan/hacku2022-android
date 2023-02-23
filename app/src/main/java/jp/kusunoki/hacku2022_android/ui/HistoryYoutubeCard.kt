package jp.kusunoki.hacku2022_android

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun HistoryYoutubeCardList() {
    LazyColumn {
        items(10) {
            HistoryYoutubeCard(onCardClicked = {
                Log.d("YoutubeCard", "Card clicked!")
            })
        }
    }
}

@Composable
fun HistoryYoutubeCard(onCardClicked: () -> Unit) {
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .width(LocalConfiguration.current.screenWidthDp.dp)
            .clickable(onClick = onCardClicked)
            .padding(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    ) {
        HistoryCardContent()
    }
}

@Composable
fun HistoryCardContent() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = "サムネ画像",
            modifier = Modifier
                .padding(8.dp)
                .height(118.dp)
                .width(215.dp),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
        Column {
            Text(
                "タイトルタイトルタイトルタイトル",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                maxLines = 1, // 最大1行に制限する
                overflow = TextOverflow.Ellipsis // 最大行数を超えた場合に末尾を省略する
            )
            Text(
                "チャンネルチャンネルチャンネルチャンネルチャンネル",
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                maxLines = 1, // 最大1行に制限する
                overflow = TextOverflow.Ellipsis // 最大行数を超えた場合に末尾を省略する
            )
            Text(
                "mm月ss日に視聴",
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