package com.example.goodsaccounting.create.model.receipt_or_write_of_material

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.UISideEffect

internal sealed class CreateReceiptOrWriteOfMaterialSideEffect : UISideEffect(){
    class Message(@StringRes val message: Int) : CreateReceiptOrWriteOfMaterialSideEffect()
    object Created: CreateReceiptOrWriteOfMaterialSideEffect()
}
