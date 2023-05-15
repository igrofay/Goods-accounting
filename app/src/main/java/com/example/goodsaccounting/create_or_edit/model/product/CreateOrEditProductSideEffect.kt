package com.example.goodsaccounting.create_or_edit.model.product

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.mvi.UISideEffect

internal sealed class CreateOrEditProductSideEffect : UISideEffect(){
    object Created : CreateOrEditProductSideEffect()
    object Edited: CreateOrEditProductSideEffect()
    class Message(@StringRes val message: Int) : CreateOrEditProductSideEffect()
}
