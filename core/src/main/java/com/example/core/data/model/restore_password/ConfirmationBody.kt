package com.example.core.data.model.restore_password

import com.example.core.domain.model.restore_password.ConfirmationModel
import kotlinx.serialization.Serializable

@Serializable
internal data class ConfirmationBody(
    override val email: String,
    override val recoveryCode: String
) : ConfirmationModel{
    companion object{
        fun ConfirmationModel.fromModelToConfirmationBody() = ConfirmationBody(email, recoveryCode)
    }
}