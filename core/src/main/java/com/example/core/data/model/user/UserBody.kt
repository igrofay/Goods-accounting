package com.example.core.data.model.user

import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.model.user.UserModel
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
internal data class UserBody(
    @SerialName("firstName")
    override val firstname: String,
    @SerialName("lastName")
    override val lastname: String,
    override val patronymic: String,
    override val phone: String,
    override val email: String,
    override val imageUrl: String?,
    override val role: RoleLevel,
) : UserModel

internal fun UserModel.fromModelToUserBody() = UserBody(
    firstname, lastname, patronymic, phone, email, imageUrl, role
)