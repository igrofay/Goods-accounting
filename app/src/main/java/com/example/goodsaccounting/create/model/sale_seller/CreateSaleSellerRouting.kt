package com.example.goodsaccounting.create.model.sale_seller

import com.example.goodsaccounting.nav.model.AppRouting

internal sealed class CreateSaleSellerRouting(route: String) : AppRouting(route){
    object FillingInFieldsCreateSaleSeller : CreateSaleSellerRouting("filling_in_fields_sale_seller")
    object ChoiceOfProducts : CreateSaleSellerRouting("choice_of_products")
}
