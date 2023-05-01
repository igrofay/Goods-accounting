package com.example.goodsaccounting.create.model.product

import com.example.goodsaccounting.common.model.UIEvent

internal sealed class CreateProductEvent : UIEvent(){
    class SelectImage(val imageUri: String) : CreateProductEvent()

}
