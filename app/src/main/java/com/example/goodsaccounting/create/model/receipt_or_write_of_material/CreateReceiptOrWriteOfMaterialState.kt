package com.example.goodsaccounting.create.model.receipt_or_write_of_material

import com.example.core.domain.model.product.MaterialModel
import com.example.goodsaccounting.common.model.UIState

internal data class CreateReceiptOrWriteOfMaterialState(
    val isReceipt: Boolean,
    val isCreating: Boolean = false,
    val name: String = "",
    val isErrorName: Boolean = false,
    val materials: Map<String, String> = mapOf(), // id material to amount
    val listImageUri: List<String> = listOf(),
    val materialsForAddInList: Map<String, MaterialModel> = mapOf(), // id material to material
    val isErrorAmountOfMaterial: Map<String, Boolean> = mapOf(),
) : UIState()
