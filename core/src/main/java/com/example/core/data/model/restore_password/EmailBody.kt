package com.example.core.data.model.restore_password

import com.example.core.domain.model.restore_password.EmailModel
import kotlinx.serialization.Serializable

@Serializable
data class EmailBody(
    override val email: String
) : EmailModel{
    companion object{
        fun EmailModel.fromModelToEmailBody() = EmailBody(email)
    }
}
