package com.example.core.data.model.auth

@kotlinx.serialization.Serializable
internal data class RestoreBody(
    val token: String
)
