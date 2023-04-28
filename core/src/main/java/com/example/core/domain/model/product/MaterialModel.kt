package com.example.core.domain.model.product

interface MaterialModel {
    val id: String
    val name: String
    val measurements: Measurements
    val imageUrl: String?
}