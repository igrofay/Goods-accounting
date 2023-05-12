package com.example.core.data.model.warehouse

import com.example.core.data.model.product.AmountOfMaterialBody
import com.example.core.domain.model.warehouse.ReceiptOrWriteOffMaterialModel
import kotlinx.serialization.Serializable

@Serializable
internal data class ReceiptOrWriteOffMaterialBody(
    override val listImageUrl: List<String>,
    override val name: String,
    override val listAmountOfMaterial: List<AmountOfMaterialBody>,
    override val date: String,
    override val creatorName: String
) : ReceiptOrWriteOffMaterialModel