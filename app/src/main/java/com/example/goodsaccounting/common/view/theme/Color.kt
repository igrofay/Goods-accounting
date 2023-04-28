package com.example.goodsaccounting.common.view.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color


internal val Green700 = Color(0xFF91BF42)
internal val Green500 = Color(0xFFB8FF40)
internal val Orange700 = Color(0xFFFF7F00)
internal val Orange500 = Color(0xFFFF9F40)
internal val While100 = Color(0xE6FFFFFF)
internal val While200 = Color(0xFFF4F4F5)
internal val Black900 = Color(0xFF1D2533)
internal val Black700 = Color(0xFF3D4555)
internal val Red700 = Color(0xFFFF0C0C)
internal val Red500 = Color(0xFFFF4747)
internal val Colors.textColor: Color
    get() = if (isLight) Black900 else While100