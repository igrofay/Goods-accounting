package com.example.core.domain.use_case.admin

import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.model.user.UserModel
import com.example.core.domain.repos.AdminRepos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUsersUseCase(
    private val adminRepos: AdminRepos,
) {
    suspend fun execute() = runCatching< Map<RoleLevel, List<UserModel>> >{
        val answer = RoleLevel.values().associateWith { mutableListOf<UserModel>() }
        for (item in adminRepos.getUsers()) {
            answer[item.role]!!.add(item)
        }
        answer
    }
}