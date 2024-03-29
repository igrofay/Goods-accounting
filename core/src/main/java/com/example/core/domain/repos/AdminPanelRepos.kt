package com.example.core.domain.repos

import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.model.user.UserModel

interface AdminPanelRepos {
    suspend fun getUsers() : List<UserModel>
    suspend fun updateUserRole(email: String, newRoleLevel: RoleLevel)
}