package com.example.core.domain.repos

import com.example.core.domain.model.restore_password.ConfirmationModel
import com.example.core.domain.model.restore_password.EmailModel
import com.example.core.domain.model.restore_password.ResetPasswordModel

interface RestorePasswordRepos {
    suspend fun recovery(emailModel: EmailModel)
    suspend fun confirmation(confirmationModel: ConfirmationModel)
    suspend fun resetPassword(resetPasswordModel: ResetPasswordModel)
}