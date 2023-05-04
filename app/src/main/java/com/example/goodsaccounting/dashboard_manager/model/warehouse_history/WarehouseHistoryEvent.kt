package com.example.goodsaccounting.dashboard_manager.model.warehouse_history

import com.example.goodsaccounting.common.model.UIEvent

internal sealed class WarehouseHistoryEvent : UIEvent(){
    object Refresh : WarehouseHistoryEvent()
}
