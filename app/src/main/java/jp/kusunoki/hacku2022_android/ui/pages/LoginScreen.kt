package jp.kusunoki.hacku2022_android.ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import jp.kusunoki.hacku2022_android.LocalNavController
import jp.kusunoki.hacku2022_android.R
import jp.kusunoki.hacku2022_android.Screen
import jp.kusunoki.hacku2022_android.util.rememberFirebaseAuthLauncher
import timber.log.Timber

@Composable
fun LoginScreen() {
    val user = remember { mutableStateOf(Firebase.auth.currentUser) }
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user.value = result.user
        },
        onAuthError = {
            user.value = null
        }
    )
    val token = stringResource(id = R.string.default_web_client_id)
    val context = LocalContext.current

    Column {
        if (user.value == null) {
            Text("Welcome to あしあと!")
            OutlinedButton(
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White),
                onClick = {
                    val gso =
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(token)
                            .requestEmail()
                            .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                },
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.image), "")
                    Text("Sign in")
                }
            }
        } else {
            val navController = LocalNavController.current
            navController.navigate(Screen.Home.route)

//            Text("Welcome ${user.value!!.displayName}")
//            Button(onClick = {
//                Firebase.auth.signOut()
//                user.value = null
//                navController.navigate(Screen.Login.route)
//            }) {
//                Text("Sign out")
//            }
        }
    }
}

