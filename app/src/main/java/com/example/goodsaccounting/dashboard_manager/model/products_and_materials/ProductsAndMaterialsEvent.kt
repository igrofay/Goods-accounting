package com.example.goodsaccounting.dashboard_manager.model.products_and_materials

import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class ProductsAndMaterialsEvent : UIEvent() {
    object Refresh : ProductsAndMaterialsEvent()
}