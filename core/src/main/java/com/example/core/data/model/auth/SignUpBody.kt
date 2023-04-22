package com.example.core.data.model.auth

import com.example.core.data.utils.toSha256
import com.example.core.domain.model.auth.SignUpModel
import kotlinx.serialization.Serializable

@Serializable
internal data class SignUpBody(
    override val firstname: String,
    override val lastname: String,
    override val patronymic: String,
    override val phone: String,
    override val email: String,
    override val password: String
) : SignUpModel

internal fun SignUpModel.fromModelToSignUpBody() = SignUpBody(
    firstname = firstname,
    lastname = lastname,
    patronymic = patronymic,
    phone = phone,
    email = email,
    password = password.toSha256()
)