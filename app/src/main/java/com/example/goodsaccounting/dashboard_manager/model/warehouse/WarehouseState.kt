package com.example.goodsaccounting.dashboard_manager.model.warehouse

import com.example.core.domain.model.filter.FilterListMaterial
import com.example.core.domain.model.product.AmountOfMaterialModel
import com.example.goodsaccounting.common.model.mvi.UIState

internal data class WarehouseState(
    val filterListMaterial: FilterListMaterial = FilterListMaterial.Alphabetically,
    val listAmountOfMaterial: List<AmountOfMaterialModel> = listOf(),
    val isRefreshing: Boolean = true,
) : UIState()