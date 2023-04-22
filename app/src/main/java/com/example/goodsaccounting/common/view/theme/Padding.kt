package com.example.goodsaccounting.common.view.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
internal data class Padding(
    val small1: Dp,
    val small2: Dp,
    val medium1: Dp,
    val medium2: Dp,
    val large1: Dp,
    val large2: Dp,
    val extraLarge1 : Dp,
)

internal val PaddingMobile = Padding(
    small1 = 4.dp,
    small2 = 8.dp,
    medium1 = 16.dp,
    medium2 = 20.dp,
    large1 = 30.dp,
    large2 = 34.dp,
    extraLarge1 = 40.dp,
)

internal val PaddingTable = Padding(
    small1 = 8.dp,
    small2 = 12.dp,
    medium1 = 20.dp,
    medium2 = 24.dp,
    large1 = 34.dp,
    large2 = 38.dp,
    extraLarge1 = 44.dp
)

internal val LocalPadding = compositionLocalOf<Padding>{ error("Padding not found") }

internal val MaterialTheme.padding : Padding
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current