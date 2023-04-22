package com.example.core.data.data_soure.api


import android.util.Log
import com.example.core.data.model.auth.RestoreBody
import com.example.core.data.model.auth.SignInBody
import com.example.core.data.model.auth.SignUpBody
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

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
}