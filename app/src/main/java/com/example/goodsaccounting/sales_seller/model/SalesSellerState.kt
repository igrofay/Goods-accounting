package com.example.goodsaccounting.sales_seller.model

import com.example.core.domain.model.sale.SaleModel
import com.example.goodsaccounting.common.model.mvi.UIState

internal data class SalesSellerState(
    val isRefreshing : Boolean = true,
    val listSale: List<SaleModel> = listOf()
) : UIState()
