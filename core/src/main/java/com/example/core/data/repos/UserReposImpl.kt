package com.example.core.data.repos

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import com.example.core.data.data_soure.api.UserApi
import com.example.core.data.model.user.UserBody
import com.example.core.data.model.user.fromModelToUpdateUserBody
import com.example.core.domain.model.user.UpdateUserModel
import com.example.core.domain.model.user.UserModel
import com.example.core.domain.repos.UserRepos
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.http.ContentDisposition.Companion.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File

internal class UserReposImpl(
    private val userApi: UserApi,
    private val context: Context,
) : UserRepos {
    override suspend fun getSingleUser(): UserModel {
        return userApi.getUser().body<UserBody>()
    }

    override fun getUser(): Flow<UserModel> = flow {
        while (true){
            val answer = userApi.getUser()
            when(answer.status){
                HttpStatusCode.OK->emit(answer.body<UserBody>())
//                HttpStatusCode.NotFound -> th
            }
            delay(10_000L)
        }
    }

    override suspend fun updateUserModel(userModel: UpdateUserModel) : UserModel {
        val answer = userApi.update(userModel.fromModelToUpdateUserBody())
        when(answer.status){
            HttpStatusCode.OK-> return answer.body<UserBody>()
            else -> throw Error(answer.status.toString())
        }
    }


    override suspend fun updateImage(uriImage: String) {
        withContext(Dispatchers.IO){
            val stream = context.contentResolver.openInputStream(Uri.parse(uriImage))!!
            userApi.updateImage(stream.readBytes())
            stream.close()
        }
    }
}