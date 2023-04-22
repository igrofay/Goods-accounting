package com.example.core.domain.repos

import com.example.core.domain.model.auth.SignInModel
import com.example.core.domain.model.auth.SignUpModel
import com.example.core.domain.model.auth.TokenModel

interface AuthRepos {
    suspend fun signIn(signInModel: SignInModel) : TokenModel
    suspend fun signUp(signUpModel: SignUpModel) : TokenModel
    suspend fun restoreSession(token: String) : TokenModel
}