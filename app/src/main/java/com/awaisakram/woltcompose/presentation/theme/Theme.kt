package com.awaisakram.woltcompose.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = WoltBlue,
    secondary = WoltBlueDark,
    background = Background,
    surface = Surface,
    error = Error,
    onPrimary = OnPrimary,
    onBackground = OnBackground,
)

private val DarkColors = darkColorScheme(
    primary = WoltBlue,
)

@Composable
fun WoltComposeTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content,
    )
}