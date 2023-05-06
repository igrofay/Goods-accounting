package com.example.core.domain.model.sale

import com.example.core.domain.model.product.AmountOfProduct
import com.example.core.domain.model.product.Currency

interface SaleModel {
    val name: String
    val date : String
    val imagesUrl: List<String>
    val products: List<AmountOfProduct>
    val creatorName: String
    val id: String
    val checkPrice : Float
    val currency: Currency
}