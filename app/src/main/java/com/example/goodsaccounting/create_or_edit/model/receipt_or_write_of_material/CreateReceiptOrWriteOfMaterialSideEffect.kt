package com.example.goodsaccounting.create_or_edit.model.receipt_or_write_of_material

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.mvi.UISideEffect

internal sealed class CreateReceiptOrWriteOfMaterialSideEffect : UISideEffect(){
    class Message(@StringRes val message: Int) : CreateReceiptOrWriteOfMaterialSideEffect()
    object Created: CreateReceiptOrWriteOfMaterialSideEffect()
}
