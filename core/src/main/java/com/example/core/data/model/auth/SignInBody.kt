package com.example.core.data.model.auth

import com.example.core.data.utils.toSha256
import com.example.core.domain.model.auth.SignInModel
import kotlinx.serialization.Serializable

@Serializable
internal data class SignInBody(
    override val email: String,
    override val password: String
) : SignInModel

internal fun SignInModel.fromModelToSignInBodyBody() = SignInBody(
    email = email,
    password = password.toSha256()
)