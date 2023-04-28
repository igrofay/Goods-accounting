package com.example.core.domain.model.product

interface ProductModel {
    val id: String
    val imageUrl: String?
    val name: String
    val price: Float
    val currency: Currency
    val materials: List<AmountOfMaterialModel>
}