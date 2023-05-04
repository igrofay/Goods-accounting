package com.example.goodsaccounting.create.model.product

import com.example.goodsaccounting.common.model.UISideEffect

internal sealed class CreateProductSideEffect : UISideEffect(){
    object ProductCreated : CreateProductSideEffect()
}
