package com.example.core.data.repos

import com.example.core.data.data_soure.api.AuthApi
import com.example.core.data.model.auth.TokenBody
import com.example.core.data.model.auth.fromModelToSignInBodyBody
import com.example.core.data.model.auth.fromModelToSignUpBody
import com.example.core.domain.model.auth.SignInModel
import com.example.core.domain.model.auth.SignUpModel
import com.example.core.domain.model.auth.TokenModel
import com.example.core.domain.model.error.AuthError
import com.example.core.domain.repos.AuthRepos
import io.ktor.client.call.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.*


internal class AuthReposImpl(
    private val api: AuthApi,
) : AuthRepos {
    override suspend fun signIn(signInModel: SignInModel): TokenModel {
        try {
            val body = signInModel.fromModelToSignInBodyBody()
            return api.requestSignIn(body).body<TokenBody>()
        }catch (e: ClientRequestException){
            when(e.response.status){
                HttpStatusCode.BadRequest ->  throw AuthError.IncorrectPassword
                HttpStatusCode.NotFound -> throw AuthError.AccountNotFound
                else -> throw IllegalStateException("AuthReposImpl::signIn>>" + e.response.status.toString())
            }
        }
    }
    override suspend fun signUp(signUpModel: SignUpModel): TokenModel {
        try {
            val body = signUpModel.fromModelToSignUpBody()
           return api.requestSignUp(body).body<TokenBody>()
        }catch (e: ClientRequestException){
            when(e.response.status){
                HttpStatusCode.Conflict -> throw AuthError.AccountAlreadyRegistered
                else ->  throw IllegalStateException("AuthReposImpl::signUp>>" + e.response.status.toString())
            }
        }
    }

    override suspend fun restoreSession(token: String): TokenModel {
        try {
            return api.requestRestoreSession(token).body<TokenBody>()
        }catch (e: ClientRequestException){
            when(e.response.status){
                HttpStatusCode.NotFound -> throw AuthError.FailedToRestoreSession
                HttpStatusCode.Unauthorized -> throw AuthError.FailedToRestoreSession
                else -> throw IllegalStateException("AuthReposImpl::restoreSession>>" + e.response.status.toString())
            }
        }
    }
}