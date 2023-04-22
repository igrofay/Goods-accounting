package com.example.goodsaccounting.auth.model

import com.example.goodsaccounting.common.model.UIEvent

internal sealed class AuthEvent : UIEvent() {
    object SetSignIn : AuthEvent()
    object SetSingUp: AuthEvent()
    // For Sign In
    class InputEmail(val email:String): AuthEvent()
    class InputPassword(val password: String) : AuthEvent()
    // For Sign Up
    class InputFirstname(val firstname:String) : AuthEvent()
    class InputLastname(val lastname: String) : AuthEvent()
    class InputPatronymic(val patronymic: String): AuthEvent()
    class InputPhone(val phone:String) : AuthEvent()
    object Authorization : AuthEvent()
    object RestorePassword : AuthEvent()
}