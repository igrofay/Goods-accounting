package com.example.goodsaccounting.auth.model

import com.example.goodsaccounting.common.model.mvi.UIState



internal data class AuthState(
    val isLoading: Boolean = false,
    val type: AuthType = AuthType.SignIn,
    // For Sign In
    val email: String = "",
    val isErrorEmail: Boolean = false,
    val password: String = "",
    val isErrorPassword: Boolean = false,
    // For Sign Up
    val firstname : String = "",
    val isErrorFirstname: Boolean = false,
    val lastname : String = "",
    val isErrorLastname: Boolean = false,
    val patronymic : String = "",
    val isErrorPatronymic: Boolean = false,
    val phone : String = "",
    val isErrorPhone: Boolean = false,
) : UIState()
