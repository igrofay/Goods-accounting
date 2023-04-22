package com.example.core.domain.model.app

import com.example.core.domain.model.user.RoleLevel

sealed class AppState {
    object Unauthorized : AppState()
    class UserContent(role: RoleLevel) : AppState()
}