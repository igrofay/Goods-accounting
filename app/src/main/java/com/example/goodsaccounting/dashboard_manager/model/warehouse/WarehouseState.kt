package com.example.goodsaccounting.dashboard_manager.model.warehouse

import com.example.goodsaccounting.common.model.UIState

internal data class WarehouseState(
    val list: List<Unit> = listOf()
) : UIState()