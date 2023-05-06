package com.example.goodsaccounting.create.model.sale_seller

import com.example.core.domain.model.product.Currency
import com.example.goodsaccounting.common.model.UIEvent

internal sealed class CreateSaleSellerEvent : UIEvent(){
    class SelectImage(val position: Int, val uri: String) : CreateSaleSellerEvent()
    class RemoveImage(val position: Int) : CreateSaleSellerEvent()
    class InputName(val name: String) : CreateSaleSellerEvent()
    object Create:CreateSaleSellerEvent()
    class AddProduct(val id: String) :  CreateSaleSellerEvent()
    class RemoveProduct(val id: String) : CreateSaleSellerEvent()
    class InputAmountOfProduct(val id: String, val amount: String) : CreateSaleSellerEvent()
    class SelectCurrency(val currency: Currency) : CreateSaleSellerEvent()
}
