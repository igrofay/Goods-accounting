package com.example.core.domain.model.error


sealed class AuthError : AppError(){
    object AccountAlreadyRegistered : AuthError()
    object IncorrectPassword : AuthError()
    object AccountNotFound: AuthError()
    object FailedToRestoreSession: AuthError()
    object NullRefreshToken: AuthError()
}
