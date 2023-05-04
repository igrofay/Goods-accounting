package com.example.core.domain.model.warehouse

import com.example.core.domain.model.product.AmountOfMaterialModel

interface ReceiptOrWriteOffMaterialModel {
    val listImageUrl: List<String>
    val name : String
    val listAmountOfMaterial: List<AmountOfMaterialModel>
    val date: String
    val creatorName: String
}