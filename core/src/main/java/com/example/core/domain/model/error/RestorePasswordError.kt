package com.example.core.domain.model.error

sealed class RestorePasswordError : AppError() {
    object UserDoesNotExist : RestorePasswordError()
    object InvalidCodeOrCodeTimedOut : RestorePasswordError()
}