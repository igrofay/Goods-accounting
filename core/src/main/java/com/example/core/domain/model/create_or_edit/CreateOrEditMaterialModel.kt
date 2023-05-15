package com.example.core.domain.model.create_or_edit

import com.example.core.domain.model.product.Measurements

interface CreateOrEditMaterialModel {
    val name: String
    val measurement: Measurements
    val minimumQuantity : Float
}