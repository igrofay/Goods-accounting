package com.example.goodsaccounting.restore_password.model

import com.example.goodsaccounting.common.model.mvi.UIState

internal data class RestorePasswordState(
    val email: String = "",
    val isErrorEmail: Boolean = false,
    val codeIsSending : Boolean = false,
    val code: String ="",
    val isErrorCode: Boolean = false,
    val newPassword: String = "",
    val isErrorNewPassword: Boolean = false,
    val newPasswordReplay: String = "",
    val isErrorNewPasswordReplay: Boolean = false,
    val isResettingPassword: Boolean = false,
) : UIState()
