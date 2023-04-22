package com.example.goodsaccounting.profile.model

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.UISideEffect

internal sealed class ProfileSideEffect : UISideEffect(){
    class ShowMessage(@StringRes val message: Int) : ProfileSideEffect()
}
