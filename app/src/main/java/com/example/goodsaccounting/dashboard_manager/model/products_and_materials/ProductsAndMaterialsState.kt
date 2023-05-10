package com.example.goodsaccounting.dashboard_manager.model.products_and_materials

import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.model.product.ProductModel
import com.example.goodsaccounting.common.model.mvi.UIState

internal data class ProductsAndMaterialsState(
    val listProductModel: List<ProductModel> = listOf(),
    val listMaterialModel: List<MaterialModel> = listOf(),
    val isRefreshing: Boolean = true,
) : UIState()