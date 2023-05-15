package com.example.core.domain.model.create_or_edit

import com.example.core.domain.model.product.Currency

interface CreateOrEditSaleModel {
    val name: String
    val products : List<AmountOfIdModel> // id product
    val checkPrice : Float
    val currency: Currency
}