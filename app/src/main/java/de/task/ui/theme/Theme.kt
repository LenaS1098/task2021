package de.task.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)


private val LightColors = lightColors(
    primary = Purple500,
    primaryVariant = Color(0xFFA6B1E1),
    secondary = Color(0xFFA7C5EB),
    secondaryVariant = LightYellow,
    background = LightWhite,
    surface = Color(0xFFa7adba ),
    onPrimary = Color.Black,
    onSurface = Color.Black
)

private val DarkColors = darkColors(
    primary = Purple500,
    primaryVariant = MediumPurple,
    secondary = MediumYellow,
    secondaryVariant = LightYellow,
    background = Color(0xFF202020 ),
    surface = Color.Black,
    onPrimary = Color.Black
)


@Composable
fun Task2021Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}