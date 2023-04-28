package com.example.core.data.repos

import android.content.Context
import android.net.Uri
import com.example.core.data.data_soure.api.UserApi
import com.example.core.data.model.user.UserBody
import com.example.core.data.model.user.fromModelToUpdateUserBody
import com.example.core.domain.model.user.UpdateUserModel
import com.example.core.domain.model.user.UserModel
import com.example.core.domain.repos.UserRepos
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class UserReposImpl(
    private val userApi: UserApi,
    private val context: Context,
) : UserRepos {
    override suspend fun getUser(): UserModel {
        return userApi.getUser().body<UserBody>()
    }

    override suspend fun updateUserModel(userModel: UpdateUserModel) : UserModel {
        val answer = userApi.update(userModel.fromModelToUpdateUserBody())
        when(answer.status){
            HttpStatusCode.OK-> return answer.body<UserBody>()
            else -> throw Error(answer.status.toString())
        }
    }


    override suspend fun updateImage(imageUri: String) {
        withContext(Dispatchers.IO){
            val stream = context.contentResolver.openInputStream(Uri.parse(imageUri))!!
            userApi.updateImage(stream.readBytes())
            stream.close()
        }
    }
}