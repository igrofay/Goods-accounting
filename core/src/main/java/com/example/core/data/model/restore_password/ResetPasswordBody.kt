package com.example.core.data.model.restore_password

import com.example.core.domain.model.restore_password.ResetPasswordModel
import kotlinx.serialization.Serializable

@Serializable
internal data class ResetPasswordBody(
    override val email: String,
    override val recoveryCode: String,
    override val password: String
) : ResetPasswordModel{
    companion object{
        fun ResetPasswordModel.fromModelToResetPasswordBody() = ResetPasswordBody(email, recoveryCode, password)
    }
}
