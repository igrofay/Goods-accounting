package com.example.goodsaccounting.create.model.sale_seller

import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.ProductModel
import com.example.goodsaccounting.common.model.UIState

internal data class CreateSaleSellerState(
    val listImageUri: List<String> = listOf(),
    val name: String = "",
    val isErrorName: Boolean = false,
    val products: Map<String, Int?> = mapOf(), // id product to amount
    val isErrorAmountOfProduct: Map<String, Boolean> = mapOf(),
    val checkPrice : Float = 0f,
    val currency: Currency = Currency.Rub,
    val listProductForAdd: Map<String, ProductModel> = mapOf(), // id product to product
    val isCreating: Boolean = false,

) : UIState()
