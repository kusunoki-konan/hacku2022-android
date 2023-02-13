package jp.kusunoki.hacku2022_android

import android.view.RoundedCorner
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun YoutubeCard(){
//// A surface container using the 'background' color from the theme
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colors.background
//    ) {
//    }
    Column {
        //Text("おすすめの講座")
        //Text("新着の講座")
        //Text("人気の講座")
    }
    Box{
        RoundedRectangle()
        Column() {
            Row(){
                Circle()
                Text("youtuber講座",textAlign = TextAlign.Center,)
            }
            Text("超人気youtuberから学ぶyoutuberになるには！")
        }
    }
}
//角丸の四角形
@Composable
fun RoundedRectangle() {
    Box(
        modifier = Modifier
            .padding(15.dp)
            .height(223.dp)
            .width(215.dp)
            .clip(RoundedCornerShape(7))
            .background(LightGray)
    )
}
//円
@Composable
fun Circle() {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(50))
            .background(Gray)
    )
}