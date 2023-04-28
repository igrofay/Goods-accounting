package com.example.core.domain.model.create

import com.example.core.domain.model.product.Currency

interface CreateProductModel {
    val name: String
    val price: Float
    val currency: Currency
    val materials: List<AmountOfIdModel>
}