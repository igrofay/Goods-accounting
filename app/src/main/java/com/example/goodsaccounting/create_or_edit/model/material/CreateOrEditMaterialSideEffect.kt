package com.example.goodsaccounting.create_or_edit.model.material

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.mvi.UISideEffect

internal sealed class CreateOrEditMaterialSideEffect : UISideEffect() {
    class ShowMessage(@StringRes val message: Int) : CreateOrEditMaterialSideEffect()
    object Created : CreateOrEditMaterialSideEffect()
    object Edited : CreateOrEditMaterialSideEffect()
}