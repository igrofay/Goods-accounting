package com.example.goodsaccounting.profile.model

import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.model.user.UserModel
import com.example.goodsaccounting.common.model.UIState

internal sealed class ProfileState : UIState() {
    object LoadProfileInfo : ProfileState()
    data class UserData(
        override val firstname: String,
        override val lastname: String,
        override val patronymic: String,
        override val phone: String,
        override val email: String,
        override val imageUrl: String?,
        override val role: RoleLevel,
    ) : UserModel, ProfileState()
    data class EditingUserData(
        override val firstname: String,
        override val lastname: String,
        override val patronymic: String,
        override val phone: String,
        override val email: String,
        override val imageUrl: String?,
        override val role: RoleLevel,
        val isErrorFirstname: Boolean = false,
        val isErrorLastname: Boolean = false,
        val isErrorPatronymic: Boolean = false,
        val isErrorPhone: Boolean = false,
        val isUpdating: Boolean = false,
    ): UserModel, ProfileState()
}

internal fun UserModel.fromModelToUserData()= ProfileState.UserData(
    firstname, lastname, patronymic, phone, email, imageUrl, role
)

internal fun ProfileState.UserData.toEditMode() = ProfileState.EditingUserData(
    firstname, lastname, patronymic, phone, email, imageUrl, role
)