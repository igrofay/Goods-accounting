package com.example.core.domain.model.restore_password

interface ResetPasswordModel {
    val email: String
    val recoveryCode: String
    val password : String
}