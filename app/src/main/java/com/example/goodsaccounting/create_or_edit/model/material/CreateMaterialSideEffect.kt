package com.example.goodsaccounting.create_or_edit.model.material

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.mvi.UISideEffect

internal sealed class CreateMaterialSideEffect : UISideEffect() {
    class ShowMessage(@StringRes val message: Int) : CreateMaterialSideEffect()
    object MaterialCreated : CreateMaterialSideEffect()
}