package com.example.goodsaccounting.dashboard_manager.model.warehouse_history

import com.example.core.domain.model.sale.SaleModel
import com.example.core.domain.model.warehouse.ReceiptOrWriteOffMaterialModel
import com.example.goodsaccounting.common.model.mvi.UIState

internal data class WarehouseHistoryState(
    val isRefreshing: Boolean = true,
    val receiptMaterial: List<ReceiptOrWriteOffMaterialModel> = listOf(),
    val writeOffMaterial: List<ReceiptOrWriteOffMaterialModel> = listOf(),
    val sales: List<SaleModel> = listOf()
) : UIState()
