package com.example.goodsaccounting.sales_seller.model

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.mvi.UISideEffect

internal sealed class SalesSellerSideEffect : UISideEffect(){
    class Message(@StringRes message: Int) : SalesSellerSideEffect()
}
