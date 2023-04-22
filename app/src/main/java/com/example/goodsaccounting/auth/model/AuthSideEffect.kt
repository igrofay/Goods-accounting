package com.example.goodsaccounting.auth.model

import androidx.annotation.StringRes
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.common.model.UISideEffect

internal sealed class AuthSideEffect : UISideEffect() {
    object GoToRestorePassword: AuthSideEffect()
    data class GoToUserContent(val roleLevel: RoleLevel): AuthSideEffect()
    class ShowMessage(@StringRes val message: Int) : AuthSideEffect()
}