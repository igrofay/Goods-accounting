package com.example.core.data.repos

import com.example.core.data.data_soure.api.AuthApi
import com.example.core.data.model.restore_password.ConfirmationBody.Companion.fromModelToConfirmationBody
import com.example.core.data.model.restore_password.EmailBody.Companion.fromModelToEmailBody
import com.example.core.data.model.restore_password.ResetPasswordBody.Companion.fromModelToResetPasswordBody
import com.example.core.domain.model.error.RestorePasswordError
import com.example.core.domain.model.restore_password.ConfirmationModel
import com.example.core.domain.model.restore_password.EmailModel
import com.example.core.domain.model.restore_password.ResetPasswordModel
import com.example.core.domain.repos.RestorePasswordRepos
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode

internal class RestorePasswordReposImpl(
    private val authApi: AuthApi,
) : RestorePasswordRepos {
    override suspend fun recovery(emailModel: EmailModel) {
        try {
            authApi.passwordRecovery(emailModel.fromModelToEmailBody())
        }catch (e: ClientRequestException){
            throw when(e.response.status){
                HttpStatusCode.NotFound -> RestorePasswordError.UserDoesNotExist
                else -> e
            }
        }

    }

    override suspend fun confirmation(confirmationModel: ConfirmationModel) {
        try {
            authApi.emailConfirmation(confirmationModel.fromModelToConfirmationBody())
        }catch (e: ClientRequestException){
            throw when(e.response.status){
                HttpStatusCode.BadRequest -> RestorePasswordError.InvalidCodeOrCodeTimedOut
//                HttpStatusCode.NotFound -> RestorePasswordError.UserDoesNotExist
                else -> e
            }
        }
    }

    override suspend fun resetPassword(resetPasswordModel: ResetPasswordModel) {
        try {
            authApi.resetPassword(resetPasswordModel.fromModelToResetPasswordBody())
        }catch (e: ClientRequestException){
            throw when(e.response.status){
                HttpStatusCode.BadRequest -> RestorePasswordError.InvalidCodeOrCodeTimedOut
//                HttpStatusCode.NotFound -> RestorePasswordError.UserDoesNotExist
                else -> e
            }
        }
    }
}