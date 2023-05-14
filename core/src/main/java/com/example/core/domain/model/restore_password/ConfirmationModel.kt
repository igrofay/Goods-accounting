package com.example.core.domain.model.restore_password

interface ConfirmationModel {
    val email: String
    val recoveryCode: String
}