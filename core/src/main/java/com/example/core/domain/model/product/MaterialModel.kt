package com.example.core.domain.model.product

interface MaterialModel {
    val id: String
    val name: String
    val measurement: Measurements
    val imageUrl: String?
    val minimumQuantity: Float
}