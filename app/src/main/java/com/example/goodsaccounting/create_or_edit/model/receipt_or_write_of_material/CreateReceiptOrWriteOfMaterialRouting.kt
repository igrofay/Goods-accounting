package com.example.goodsaccounting.create_or_edit.model.receipt_or_write_of_material

import com.example.goodsaccounting.nav.model.AppRouting

internal sealed class CreateReceiptOrWriteOfMaterialRouting(route: String) : AppRouting(route) {
    object FillingInFieldsReceiptOrWriteOffMaterial : CreateReceiptOrWriteOfMaterialRouting("filling_in_fields_receipt_or_write_off_material")
    object ChoiceOfMaterials : CreateReceiptOrWriteOfMaterialRouting("choice_of_materials")
}