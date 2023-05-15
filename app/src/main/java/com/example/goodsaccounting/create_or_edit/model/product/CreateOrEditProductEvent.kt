package com.example.goodsaccounting.create_or_edit.model.product

import com.example.core.domain.model.product.Currency
import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class CreateOrEditProductEvent : UIEvent(){
    object CreateOrEdit : CreateOrEditProductEvent()

    class SelectImage(val imageUri: String) : CreateOrEditProductEvent()
    class SelectCurrency(val currency: Currency): CreateOrEditProductEvent()
    class InputName(val name: String) : CreateOrEditProductEvent()
    class InputPrice(val price: String) : CreateOrEditProductEvent()

    class AddMaterial(val id: String): CreateOrEditProductEvent()

    class RemoveMaterial(val id: String) : CreateOrEditProductEvent()

    class InputAmountMaterial(val amount: String, val id: String) : CreateOrEditProductEvent()
}
