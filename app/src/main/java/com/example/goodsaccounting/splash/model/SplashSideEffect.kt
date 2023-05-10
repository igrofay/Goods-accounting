package com.example.goodsaccounting.splash.model

import androidx.annotation.StringRes
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.common.model.mvi.UISideEffect

internal sealed class SplashSideEffect : UISideEffect(){
    object GoToAuthentication : SplashSideEffect()
    class GoToUserContent(val role: RoleLevel) : SplashSideEffect()
    class ShowSnackbar(@StringRes val message: Int) : SplashSideEffect()
}
