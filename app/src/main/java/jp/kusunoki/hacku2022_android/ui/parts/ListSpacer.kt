package jp.kusunoki.hacku2022_android.ui.parts

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListSpacer(
    modifier: Modifier = Modifier,
    spacerColor: Color = if(isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
    isStraight: Boolean = true
) {
    if (isStraight) {
        Divider(
            color = spacerColor.copy(alpha = 0.5f),
            modifier = modifier,
            thickness = 1.dp
        )
    } else {
        Divider(
            color = spacerColor.copy(alpha = 0.5f),
            modifier = modifier,
            startIndent = 8.dp,
            thickness = 1.dp
        )
    }
}