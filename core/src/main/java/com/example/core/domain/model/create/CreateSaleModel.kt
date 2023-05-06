package com.example.core.domain.model.create

import com.example.core.domain.model.product.Currency

interface CreateSaleModel {
    val name: String
    val products : List<AmountOfIdModel> // id product
    val checkPrice : Float
    val currency: Currency
}