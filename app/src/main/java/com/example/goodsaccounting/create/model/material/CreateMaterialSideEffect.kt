package com.example.goodsaccounting.create.model.material

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.UISideEffect

internal sealed class CreateMaterialSideEffect : UISideEffect() {
    class ShowMessage(@StringRes val message: Int) : CreateMaterialSideEffect()
    object Exit : CreateMaterialSideEffect()
}