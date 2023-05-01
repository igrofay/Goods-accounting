package com.example.goodsaccounting.create.model.product

import com.example.core.domain.model.product.Currency
import com.example.goodsaccounting.common.model.UIEvent

internal sealed class CreateProductEvent : UIEvent(){
    object Create : CreateProductEvent()

    class SelectImage(val imageUri: String) : CreateProductEvent()
    class SelectCurrency(val currency: Currency): CreateProductEvent()
    class InputName(val name: String) : CreateProductEvent()
    class InputPrice(val price: String) : CreateProductEvent()
}
