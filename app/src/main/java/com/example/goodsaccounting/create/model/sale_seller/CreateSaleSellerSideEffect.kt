package com.example.goodsaccounting.create.model.sale_seller

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.UISideEffect

internal sealed class CreateSaleSellerSideEffect : UISideEffect() {
    class Message(@StringRes val stringRes: Int) : CreateSaleSellerSideEffect()
    object Created : CreateSaleSellerSideEffect()
}