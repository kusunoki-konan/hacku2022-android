package jp.kusunoki.hacku2022_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.kusunoki.hacku2022_android.ui.pages.HistoryScreen
import jp.kusunoki.hacku2022_android.ui.pages.HomeScreen
import jp.kusunoki.hacku2022_android.ui.pages.LoginScreen
import jp.kusunoki.hacku2022_android.ui.pages.VideoScreen
import jp.kusunoki.hacku2022_android.ui.theme.Hacku2022androidTheme
import jp.kusunoki.hacku2022_android.ui.theme.Primary
import jp.kusunoki.hacku2022_android.util.youtubeTime
import jp.kusunoki.hacku2022_android.util.youtubeVideoId
import timber.log.Timber

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No Current NavController")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hacku2022androidTheme {
                val navController = rememberNavController()

                CompositionLocalProvider(
                    LocalNavController provides navController
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            MainBottomNavigation()
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            NavHost(
                                navController = LocalNavController.current,
                                startDestination = Screen.Home.route
                            ) {
                                composable(Screen.Login.route) {
                                    LoginScreen()
                                }
                                composable(Screen.Home.route) {
                                    HomeScreen()
                                }
                                composable(Screen.History.route) {
                                    HistoryScreen()
                                }
                                composable(
                                    route = "${Screen.Video.route}/{videoUrl}",
                                ) { backStackEntry ->
                                    val videoUrl = backStackEntry.arguments?.getString("videoUrl") ?: ""
                                    val videoId = videoUrl.youtubeVideoId()
                                    val second = videoUrl.youtubeTime()

                                    VideoScreen(videoId, second)
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

sealed class Screen(val route: String, val icon: ImageVector = Icons.Default.Home, @StringRes val resourceId: Int = R.string.app_name) {
    object Login: Screen("login")
    object Home: Screen("home", Icons.Default.Home, R.string.home_screen)
    object History: Screen("history", Icons.Default.History, R.string.history_screen)
    object Video: Screen("video")
}

@Composable
fun MainBottomNavigation() {
    val items = listOf(Screen.Home, Screen.History)
    val navController = LocalNavController.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val homeOrHistory = currentDestination?.hierarchy?.any {
        it.route == Screen.Home.route || it.route == Screen.History.route
    }

    if(homeOrHistory == true) {
        BottomNavigation(
            // コメントアウトは従来のデザイン
            backgroundColor = Color.White
        ) {
            items.forEach { screen ->
                val selected = currentDestination.hierarchy.any { it.route == screen.route }
                BottomNavigationItem(
                    icon = {
                        Icon(
                            screen.icon,
                            contentDescription = null,
                            tint = if(selected) Primary else Color.LightGray
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = screen.resourceId),
                            color = if(selected) Color.Black else Color.LightGray
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(screen.route)
                    }
                )
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