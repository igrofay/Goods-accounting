package com.example.core.data.repos

import com.example.core.data.data_soure.api.AdminApi
import com.example.core.data.model.admin.ChangeRoleUser
import com.example.core.data.model.user.UserBody
import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.model.user.UserModel
import com.example.core.domain.repos.AdminRepos
import io.ktor.client.call.*
import io.ktor.http.*

internal class AdminReposImpl(
    private val adminApi: AdminApi
) : AdminRepos{
    override suspend fun getUsers(): List<UserModel> {
        val answer = adminApi.getUsers()
        when(answer.status) {
            HttpStatusCode.OK -> return answer.body<List<UserBody>>()
            else-> throw Error(answer.status.toString())
        }
    }

    override suspend fun updateUserRole(email: String, newRoleLevel: RoleLevel) {
        adminApi.updateRole(ChangeRoleUser(email, newRoleLevel))
    }
}