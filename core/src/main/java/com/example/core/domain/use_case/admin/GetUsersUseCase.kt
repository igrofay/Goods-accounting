package com.example.core.domain.use_case.admin

import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.model.user.UserModel
import com.example.core.domain.repos.AdminPanelRepos

class GetUsersUseCase(
    private val adminPanelRepos: AdminPanelRepos,
) {
    suspend fun execute() = runCatching< Map<RoleLevel, List<UserModel>> >{
        val answer = RoleLevel.values().associateWith { mutableListOf<UserModel>() }
        for (item in adminPanelRepos.getUsers()) {
            answer[item.role]!!.add(item)
        }
        answer
    }
}