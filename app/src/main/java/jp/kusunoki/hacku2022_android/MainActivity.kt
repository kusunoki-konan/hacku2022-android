package jp.kusunoki.hacku2022_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.kusunoki.hacku2022_android.ui.component.pages.HistoryScreen
import jp.kusunoki.hacku2022_android.ui.component.pages.HomeScreen
import jp.kusunoki.hacku2022_android.ui.component.pages.VideoScreen
import jp.kusunoki.hacku2022_android.ui.theme.Hacku2022androidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No Current NavController")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dao = RoomApplication.database.todoDao()
    private var todoList = mutableStateListOf<Todo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hacku2022androidTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(todoList)
                }
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
                                    VideoScreen(videoUrl)
                                }
                            }
                        }
                    }
                }
            }
        }
        loadTodo()
    }
    private fun loadTodo() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.getAll().forEach { todo ->
                    todoList.add(todo)
                }
            }
        }
    }

    private fun postTodo(title: String) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.post(Todo(title = title))

                todoList.clear()
                loadTodo()
            }
        }
    }

    private fun deleteTodo(todo: Todo) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.delete(todo)

                todoList.clear()
                loadTodo()
            }
        }
    }
    @Composable
    fun MainScreen(todoList: SnapshotStateList<Todo>) {
        var text: String by remember { mutableStateOf("") }

        Column {
            TopAppBar(
                title = { Text("Todo List") }
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                items(todoList) { todo ->
                    key(todo.id) {
                        TodoItem(todo)
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { it -> text = it },
                    label = { Text("ToDo") },
                    modifier = Modifier.wrapContentHeight().weight(1f)
                )
                Spacer(Modifier.size(16.dp))
                Button(
                    onClick = {
                        if (text.isEmpty()) return@Button
                        postTodo(text)
                        text = ""
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("ADD")
                }
            }

        }
    }
    @Composable
    fun TodoItem(todo: Todo) {
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(onClick = { deleteTodo(todo) })
        ) {
            Text(
                text = todo.title,
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
            )
            Text(
                text = "created at: ${sdf.format(todo.created_at)}",
                fontSize = 12.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

sealed class Screen(val route: String, val icon: ImageVector = Icons.Default.Home, @StringRes val resourceId: Int = R.string.app_name) {
    object Home: Screen("home", Icons.Default.Home, R.string.home_screen)
    object History: Screen("history", Icons.Default.List, R.string.history_screen)
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
        BottomNavigation {
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(screen.icon, contentDescription = null)
                    },
                    label = {
                        Text(text = stringResource(id = screen.resourceId))
                    },
                    selected = currentDestination.hierarchy.any { it.route == screen.route },
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