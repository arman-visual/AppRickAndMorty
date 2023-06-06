package com.aquispe.apprickandmorty.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Green200,
    onPrimary = White,
    primaryVariant = Green700,
    secondary = Purple200
)

private val LightColorPalette = lightColors(
    primary = Green,
    onPrimary = Black,
    primaryVariant = Green700,
    secondary = Purple200,
    secondaryVariant = Purple900
)

@Composable
fun ArmandoQuispe2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
