package com.example.goodsaccounting.restore_password.model

import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class RestorePasswordEvent : UIEvent(){
    class InputEmail(val email: String): RestorePasswordEvent()
    object PushEmail : RestorePasswordEvent()
    class InputCode(val code: String) : RestorePasswordEvent()
    object SendCodeAgain: RestorePasswordEvent()
    object ClearFields : RestorePasswordEvent()
    class InputNewPassword(val password: String): RestorePasswordEvent()
    class InputNewPasswordReplay(val password: String): RestorePasswordEvent()
    object SetNewPassword: RestorePasswordEvent()
}
