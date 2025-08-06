package com.example.fixpilot.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color

@Composable
fun FixPilotTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = PrimaryColor,
            onPrimary = OnPrimaryColor,
            secondary = SecondaryColor,
            onSecondary = OnSecondaryColor,
            background = DarkBackgroundColor,
            onBackground = Color.White
        )
    } else {
        lightColorScheme(
            primary = PrimaryColor,
            onPrimary = OnPrimaryColor,
            secondary = SecondaryColor,
            onSecondary = OnSecondaryColor,
            background = LightBackgroundColor,
            onBackground = Color.Black
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
