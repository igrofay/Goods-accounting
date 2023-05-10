package com.example.goodsaccounting.user_administration.model

import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.model.user.UserModel
import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class UserAdministrationEvent : UIEvent(){
    object Refresh: UserAdministrationEvent()
    class OpenUserForChangeRole(val userModel: UserModel) : UserAdministrationEvent()
    object CloseChangeUserRole : UserAdministrationEvent()
    class SelectedNewUserRole(val roleLevel: RoleLevel) : UserAdministrationEvent()
    object SaveNewUserRole : UserAdministrationEvent()
}