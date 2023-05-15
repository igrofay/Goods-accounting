package com.example.core.domain.model.create_or_edit

import com.example.core.domain.model.product.Currency

interface CreateOrEditProductModel {
    val name: String
    val price: Float
    val currency: Currency
    val materials: List<AmountOfIdModel>
}