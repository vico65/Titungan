package com.vico.titungan.ui.theme

import android.app.Activity
import androidx.compose.material3.ColorScheme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.wear.compose.material.MaterialTheme.colors

private val DarkColorScheme = darkColorScheme(
    background = Grey,
    onBackground = White,

    primary = BluePastel,
    onPrimary = Grey,

    secondary = GreenPastel,
//    secondaryVariant = GreenPastel,
    onSecondary = Grey,

    error = SalmonPastel,
    onError = Grey,
)

private val LightColorScheme = lightColorScheme(
    background = White,
    onBackground = Grey,

    primary = Blue,
    onPrimary = Grey,

    secondary = Green,
//    secondaryVariant = Green,
    onSecondary = Grey,

    error = Salmon,
    onError = Grey,
)

@Composable
fun TitunganTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isPreviewing: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    if (!isPreviewing) {
        val activity = LocalView.current.context as Activity
        SideEffect {
            activity.window.statusBarColor = colorScheme.surface.toArgb()
            activity.window.navigationBarColor = colorScheme.surface.toArgb()
            val wic =
                WindowCompat.getInsetsController(activity.window, activity.window.decorView)
            wic.isAppearanceLightStatusBars = !isDarkTheme
            wic.isAppearanceLightNavigationBars = !isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}