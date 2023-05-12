package com.example.goodsaccounting.dashboard_manager.model.warehouse

import com.example.core.domain.model.filter.FilterListMaterial
import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class WarehouseEvent : UIEvent(){
    class SelectFilter(val filterListMaterial: FilterListMaterial) : WarehouseEvent()
    object Refresh : WarehouseEvent()
}
