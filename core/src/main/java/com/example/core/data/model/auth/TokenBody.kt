package com.example.core.data.model.auth

import com.example.core.domain.model.auth.TokenModel
import com.example.core.domain.model.user.RoleLevel
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenBody(
    override val accessToken: String,
    override val refreshToken: String,
) : TokenModel