package com.example.goodsaccounting.restore_password.model

import com.example.goodsaccounting.common.model.mvi.UIState

internal data class RestorePasswordState(
    val email: String = "",
    val code: String ="",
    val newPassword: String = "",
    val repeatNewPassword: String
) : UIState()
