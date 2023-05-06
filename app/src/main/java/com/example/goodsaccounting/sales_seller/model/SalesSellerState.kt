package com.example.goodsaccounting.sales_seller.model

import com.example.core.domain.model.sale.SaleModel
import com.example.goodsaccounting.common.model.UIState

internal data class SalesSellerState(
    val isRefresh : Boolean = true,
    val listSale: List<SaleModel> = listOf()
) : UIState()
