package com.example.core.domain.use_case.user

import com.example.core.domain.model.user.UpdateUserModel
import com.example.core.domain.model.user.UserModel
import com.example.core.domain.repos.UserRepos

class UpdateUserDataUseCase(
    private val userRepos: UserRepos,
){
    suspend fun execute(userModel: UserModel)= runCatching {
        userModel.imageUrl?.let { uri->
            if(!uri.contains("http"))
                userRepos.updateImage(uri)
        }
        userRepos.updateUserModel(
            object : UpdateUserModel{
                override val firstname = userModel.firstname
                override val lastname = userModel.lastname
                override val patronymic = userModel.patronymic
                override val phone = userModel.phone
            }
        )
    }
}