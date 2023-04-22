package com.example.goodsaccounting.user_administration.model

import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.model.user.UserModel
import com.example.goodsaccounting.common.model.UIState

internal data class UserAdministrationState(
    val users: Map<RoleLevel,List<UserModel>> = mapOf(),
    val isRefreshing: Boolean = true,
    val changeUserRole: UserModel? = null,
    val newUserRole: RoleLevel? = null,
) : UIState()