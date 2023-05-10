package com.hamid97m.herodatepicker.sample.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColors(
    primary = Purple80,
    secondary = Pink80
)

private val LightColorScheme = lightColors(
    primary = Purple40,
    secondary = Pink40
)


@Composable
fun HeroDatePickerTheme(
    isInDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isInDarkMode) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = isInDarkMode
        }
    }

    MaterialTheme(
        colors = colorScheme,
        typography = HeroDatePickerTypography,
        content = content
    )
}
