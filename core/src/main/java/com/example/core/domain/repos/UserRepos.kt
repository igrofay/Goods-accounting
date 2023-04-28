package com.example.core.domain.repos

import com.example.core.domain.model.user.UpdateUserModel
import com.example.core.domain.model.user.UserModel

interface UserRepos {
    suspend fun getUser() : UserModel
    suspend fun updateUserModel(userModel: UpdateUserModel) : UserModel
    suspend fun updateImage(imageUri: String)
}