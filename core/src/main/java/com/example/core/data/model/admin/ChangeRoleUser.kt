package com.example.core.data.model.admin

import com.example.core.domain.model.user.RoleLevel

@kotlinx.serialization.Serializable
internal data class ChangeRoleUser(
    val email: String,
    val newRoleLevel: RoleLevel,
)