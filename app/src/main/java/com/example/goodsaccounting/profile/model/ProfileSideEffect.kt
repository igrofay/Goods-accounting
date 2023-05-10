package com.example.goodsaccounting.profile.model

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.mvi.UISideEffect

internal sealed class ProfileSideEffect : UISideEffect(){
    class ShowMessage(@StringRes val message: Int) : ProfileSideEffect()
}
