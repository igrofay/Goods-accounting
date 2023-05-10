package com.example.goodsaccounting.create_or_edit.model.sale_seller

import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.ProductModel
import com.example.goodsaccounting.common.model.mvi.UIState
import com.example.goodsaccounting.create_or_edit.model.common.CreateOrEditState

internal sealed class CreateOrEditSaleSellerState : UIState(){
    object Load : CreateOrEditSaleSellerState()
    data class CreateOrEdit(
        val createOrEditState: CreateOrEditState,
        val listImageUri: List<String> = listOf(),
        val name: String = "",
        val isErrorName: Boolean = false,
        val products: Map<String, Int?> = mapOf(), // id product to amount
        val isErrorAmountOfProduct: Map<String, Boolean> = mapOf(),
        val checkPrice : Float = 0f,
        val currency: Currency = Currency.Rub,
        val listProductForAdd: Map<String, ProductModel> = mapOf(), // id product to product
        val isCreatingOrEditing: Boolean = false,
    ) : CreateOrEditSaleSellerState()
}
