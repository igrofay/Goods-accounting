package com.example.goodsaccounting.common.view.theme

import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColors(
    primary = Orange500,
    secondary = Green500,
    background = Black900,
    surface = Black700,
    error = Red500,
)

private val LightColorPalette = lightColors(
    primary = Orange700,
    secondary = Green700,
    background = While100,
    surface = While200,
    error = Red700
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
internal fun GoodsAccountingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val config = LocalConfiguration.current
    val size = min(config.screenWidthDp.dp, config.screenHeightDp.dp)
    val typography = if (size < 450.dp) {
        TypographyMobile
    } else {
        TypographyTable
    }
    val padding = if (size < 450.dp) {
        PaddingMobile
    } else {
        PaddingTable
    }
    val shapes = if (size < 450.dp) {
        ShapesMobile
    } else {
        ShapesTable
    }
    val activity = LocalContext.current as? ComponentActivity
    SideEffect {
        activity?.window?.statusBarColor = colors.background.toArgb()
        activity?.window?.let {window->
            WindowCompat
                .getInsetsController(window, window.decorView).isAppearanceLightStatusBars = !darkTheme
        }

    }
    CompositionLocalProvider(
        LocalPadding provides padding
    ){
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}