package jp.kusunoki.hacku2022_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.kusunoki.hacku2022_android.ui.theme.Hacku2022androidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hacku2022androidTheme {
                // Smoothly scroll 100px on first composition
                val state = rememberScrollState()
                LaunchedEffect(Unit) { state.animateScrollTo(0) }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .verticalScroll(state)
                ) {
                    Text("おすすめの講座",fontWeight = FontWeight.Bold,fontSize = 20.sp,modifier = Modifier.padding(all = 8.dp))
                    YoutubeCardList()
                    Text("新着の講座",fontWeight = FontWeight.Bold,fontSize = 20.sp,modifier = Modifier.padding(all = 8.dp))
                    YoutubeCardList()
                    Text("人気の講座",fontWeight = FontWeight.Bold,fontSize = 20.sp,modifier = Modifier.padding(all = 8.dp))
                    YoutubeCardList()
                }
                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
                ) {
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Hacku2022androidTheme {
        Greeting("Android")
    }
}