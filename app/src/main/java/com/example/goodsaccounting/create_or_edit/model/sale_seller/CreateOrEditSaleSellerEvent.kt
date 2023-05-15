package com.example.goodsaccounting.create_or_edit.model.sale_seller

import com.example.core.domain.model.product.Currency
import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class CreateOrEditSaleSellerEvent : UIEvent(){
    class SelectImage(val position: Int, val uri: String) : CreateOrEditSaleSellerEvent()
    class RemoveImage(val position: Int) : CreateOrEditSaleSellerEvent()
    class InputName(val name: String) : CreateOrEditSaleSellerEvent()
    object CreateOrEdit:CreateOrEditSaleSellerEvent()
    class AddProduct(val id: String) :  CreateOrEditSaleSellerEvent()
    class RemoveProduct(val id: String) : CreateOrEditSaleSellerEvent()
    class InputAmountOfProduct(val id: String, val amount: String) : CreateOrEditSaleSellerEvent()
    class SelectCurrency(val currency: Currency) : CreateOrEditSaleSellerEvent()
}
