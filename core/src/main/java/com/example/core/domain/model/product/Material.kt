package com.example.core.domain.model.product

interface Material {
    val id: String
    val name: String
    val measurements: Measurements
    val imageUrl: String?
    val extensionMaterial : List<ExtensionMaterial>
}