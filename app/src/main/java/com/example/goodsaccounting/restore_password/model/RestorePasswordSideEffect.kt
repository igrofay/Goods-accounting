package com.example.goodsaccounting.restore_password.model

import androidx.annotation.StringRes
import com.example.goodsaccounting.common.model.mvi.UISideEffect

internal sealed class RestorePasswordSideEffect : UISideEffect(){
    object GoToInputCode : RestorePasswordSideEffect()

    object GoToInputNewPassword : RestorePasswordSideEffect()
    class Message(@StringRes val message: Int) : RestorePasswordSideEffect()

    object PasswordHasBeenChanged : RestorePasswordSideEffect()
}
