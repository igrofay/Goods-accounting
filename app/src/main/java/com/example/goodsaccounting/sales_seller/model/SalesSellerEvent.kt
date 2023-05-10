package com.example.goodsaccounting.sales_seller.model

import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class SalesSellerEvent : UIEvent(){
    object Refresh : SalesSellerEvent()
}
