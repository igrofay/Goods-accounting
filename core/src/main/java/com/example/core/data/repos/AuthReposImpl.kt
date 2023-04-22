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
import io.ktor.http.*


internal class AuthReposImpl(
    private val api: AuthApi,
) : AuthRepos {
    override suspend fun signIn(signInModel: SignInModel): TokenModel {
        val body = signInModel.fromModelToSignInBodyBody()
        val answer = api.requestSignIn(body)
        return when(answer.status){
            HttpStatusCode.OK -> answer.body<TokenBody>()
            HttpStatusCode.BadRequest ->  throw AuthError.IncorrectPassword
            HttpStatusCode.NotFound -> throw AuthError.AccountNotFound
            else -> throw IllegalStateException("AuthReposImpl::signIn>>" + answer.status.toString())
        }
    }
    override suspend fun signUp(signUpModel: SignUpModel): TokenModel {
        val body = signUpModel.fromModelToSignUpBody()
        val answer = api.requestSignUp(body)
        return when(answer.status){
            HttpStatusCode.OK -> answer.body<TokenBody>()
            HttpStatusCode.Conflict -> throw AuthError.AccountAlreadyRegistered
            else ->  throw IllegalStateException("AuthReposImpl::signUp>>" + answer.status.toString())
        }
    }

    override suspend fun restoreSession(token: String): TokenModel {
        val answer = api.requestRestoreSession(token)
        return when(answer.status){
            HttpStatusCode.OK -> answer.body<TokenBody>()
            HttpStatusCode.NotFound -> throw AuthError.FailedToRestoreSession
            HttpStatusCode.Unauthorized -> throw AuthError.FailedToRestoreSession
            else -> throw IllegalStateException("AuthReposImpl::restoreSession>>" + answer.status.toString())
        }
    }
}