package com.example.goodsaccounting.create_or_edit.model.sale_seller

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.mvi.UISideEffect

internal sealed class CreateOrEditSaleSellerSideEffect : UISideEffect() {
    class Message(@StringRes val stringRes: Int) : CreateOrEditSaleSellerSideEffect()
    object Created : CreateOrEditSaleSellerSideEffect()
    object Edited : CreateOrEditSaleSellerSideEffect()
}