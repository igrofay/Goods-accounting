package com.example.goodsaccounting.dashboard_manager.view_model

import androidx.lifecycle.viewModelScope
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.dashboard_manager.model.warehouse.WarehouseEvent
import com.example.goodsaccounting.dashboard_manager.model.warehouse.WarehouseSideEffect
import com.example.goodsaccounting.dashboard_manager.model.warehouse.WarehouseState
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container

internal class WarehouseVM(
    override val di: DI
) : AppVM<WarehouseState, WarehouseSideEffect, WarehouseEvent>(), DIAware{

    override val container: Container<WarehouseState, WarehouseSideEffect> =
        viewModelScope.container(WarehouseState())

    override fun onEvent(event: WarehouseEvent) {

    }
    override fun onError(error: Throwable) {

    }
}