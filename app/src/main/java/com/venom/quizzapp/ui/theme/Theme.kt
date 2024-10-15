package com.venom.quizzapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val CustomLightColorScheme = lightColorScheme(
    background = Color.White,
    primary = Color.Black,
    secondary = Color(0xFFFFBF00),
)

private val CustomDarkColorScheme = darkColorScheme(
    background = Color(0xFF1E1E1E),
    primary = Color.White,
    secondary = Color(0xFFFFBF00),
)

@Composable
fun QuizzappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> CustomDarkColorScheme
        else -> CustomLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}