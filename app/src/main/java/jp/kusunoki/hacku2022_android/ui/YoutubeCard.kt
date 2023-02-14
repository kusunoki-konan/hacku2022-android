package jp.kusunoki.hacku2022_android

import android.view.RoundedCorner
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun YoutubeCard() {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.height(223.dp).width(215.dp).padding(16.dp).wrapContentSize(),
        backgroundColor= MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        CardContent()
    }
}
@Composable
fun CardContent() {
    Column {
        Image(
            painter = painterResource(R.drawable.image),
            contentDescription = "サムネ画像",
            modifier = Modifier
                .height(118.dp)
                .width(216.dp),
//            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
        Column() { //Modifier.padding(16.dp)
            Row(){
                Image(
                    painter = painterResource(id = R.drawable.circle),
                    contentDescription = "チャンネル画像",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
                Text(
                    "youtuber講座",
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Text(
                "超人気youtuberから学ぶyoutuberになるには！",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}