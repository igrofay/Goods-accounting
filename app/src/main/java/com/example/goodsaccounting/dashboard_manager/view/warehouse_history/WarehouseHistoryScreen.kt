package com.example.goodsaccounting.dashboard_manager.view.warehouse_history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.goodsaccounting.R
import com.example.goodsaccounting.dashboard_manager.model.LocalDashboardSetting

@Composable
internal fun WarehouseHistoryScreen() {
    val dashboardSetting = LocalDashboardSetting.current
    LaunchedEffect(dashboardSetting){
        dashboardSetting.setLabel(R.string.warehouse_history)
    }
}