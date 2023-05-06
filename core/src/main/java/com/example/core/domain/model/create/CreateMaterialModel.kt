package com.example.core.domain.model.create

import com.example.core.domain.model.product.Measurements

interface CreateMaterialModel {
    val name: String
    val measurement: Measurements
    val minimumQuantity : Float
}