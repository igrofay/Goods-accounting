package com.example.core.data.data_soure.api

import com.example.core.data.model.admin.ChangeRoleUser
import com.example.core.domain.model.user.RoleLevel
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class AdminApi(
    private val authorizedClient: HttpClient
) {
    suspend fun getUsers() = authorizedClient.get("/api/users")

    suspend fun updateRole(changeRoleUser: ChangeRoleUser) = authorizedClient.put("/api/user"){
        setBody(changeRoleUser)
        contentType(ContentType.Application.Json)
    }
}