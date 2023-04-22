package com.example.core.domain.model.auth


interface TokenModel {
    val accessToken: String
    val refreshToken: String
}