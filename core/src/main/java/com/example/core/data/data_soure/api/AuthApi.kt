package com.example.core.data.data_soure.api


import com.example.core.data.model.auth.RestoreBody
import com.example.core.data.model.auth.SignInBody
import com.example.core.data.model.auth.SignUpBody
import com.example.core.data.model.restore_password.ConfirmationBody
import com.example.core.data.model.restore_password.EmailBody
import com.example.core.data.model.restore_password.ResetPasswordBody
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class AuthApi(
    private val baseClient: HttpClient,
){
    suspend fun requestSignUp(signUp: SignUpBody) = baseClient.post("/api/signup"){
        setBody(signUp)
        contentType(ContentType.Application.Json)
    }
    suspend fun requestSignIn(signIn: SignInBody) = baseClient.post("/api/signin"){
        setBody(signIn)
        contentType(ContentType.Application.Json)
    }
    suspend fun requestRestoreSession(token: String) = baseClient.post("/api/token"){
        setBody(RestoreBody(token))
        contentType(ContentType.Application.Json)
    }
    suspend fun passwordRecovery(emailBody: EmailBody) = baseClient.post("/api/recovery"){
        setBody(emailBody)
        contentType(ContentType.Application.Json)
    }
    suspend fun emailConfirmation(confirmationBody: ConfirmationBody) = baseClient.post("/api/confirmation"){
        setBody(confirmationBody)
        contentType(ContentType.Application.Json)
    }
    suspend fun resetPassword(resetPasswordBody: ResetPasswordBody) = baseClient.post("/api/reset"){
        setBody(resetPasswordBody)
        contentType(ContentType.Application.Json)
    }
}