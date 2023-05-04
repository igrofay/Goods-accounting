package com.example.goodsaccounting.dashboard_manager.model.dashboard

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf

internal class DashboardSetting {

    private val _labelTopBar = mutableStateOf<Int?>(null)
    val labelTopBar by _labelTopBar


    fun setLabel(@StringRes label: Int) {
        _labelTopBar.value = label
    }
}

internal val LocalDashboardSetting = staticCompositionLocalOf<DashboardSetting> { error("Not Found DashboardSetting") }