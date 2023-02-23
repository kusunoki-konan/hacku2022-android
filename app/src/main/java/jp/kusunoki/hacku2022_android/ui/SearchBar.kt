package jp.kusunoki.hacku2022_android.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.kusunoki.hacku2022_android.R

@Composable
fun SearchBar(
    textFieldState: MutableState<TextFieldValue>,
    onSubmit: (text: String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = textFieldState.value,
        onValueChange = { value ->
            textFieldState.value = value
        },
        placeholder = { Text(stringResource(R.string.video_url_search)) },
        shape = MaterialTheme.shapes.medium.copy(CornerSize(16.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),//入力したときのカラー
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "search icon",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (textFieldState.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        textFieldState.value =
                            TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "delete icon",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,//フォーカスされたときの下線
            unfocusedBorderColor = Color.Transparent,//フォーカスされていないときの下線
            cursorColor = Color.Black,//カーソル
            backgroundColor = Color.LightGray,//背景
            leadingIconColor = Color.Black,//虫眼鏡アイコン
            trailingIconColor = Color.Black,//✕アイコン)
        ),
        keyboardActions = KeyboardActions {
            focusManager.clearFocus()
            onSubmit(textFieldState.value.text)
        }
    )
}