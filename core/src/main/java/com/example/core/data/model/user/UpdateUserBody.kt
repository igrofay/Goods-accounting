package com.example.core.data.model.user

import com.example.core.domain.model.user.UpdateUserModel

@kotlinx.serialization.Serializable
internal data class UpdateUserBody(
    override val firstname: String,
    override val lastname: String,
    override val patronymic: String,
    override val phone: String
) : UpdateUserModel

internal fun UpdateUserModel.fromModelToUpdateUserBody() = UpdateUserBody(
    firstname, lastname, patronymic, phone
)