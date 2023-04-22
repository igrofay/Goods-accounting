package com.example.core.domain.repos

import com.example.core.domain.model.user.UpdateUserModel
import com.example.core.domain.model.user.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepos {
    suspend fun getSingleUser() : UserModel
    fun getUser(): Flow<UserModel>
    suspend fun updateUserModel(userModel: UpdateUserModel) : UserModel
    suspend fun updateImage(uriImage: String)
}