package com.example.goodsaccounting.restore_password.model

import com.example.goodsaccounting.nav.model.AppRouting

internal sealed class RestorePasswordRouting(route: String) : AppRouting(route) {
    object InputEmail : RestorePasswordRouting("${route}_input_email")
    object InputCode : RestorePasswordRouting("${route}_input_code")
    object InputNewPassword : RestorePasswordRouting("${route}_input_new_password")
    companion object{
        val route = "restore_password"
    }
}