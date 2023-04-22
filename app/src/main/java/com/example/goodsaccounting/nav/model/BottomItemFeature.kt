package com.example.goodsaccounting.nav.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface BottomItemFeature {
    @get:StringRes
    val label: Int
    @get:DrawableRes
    val icon: Int
    val route: String
}