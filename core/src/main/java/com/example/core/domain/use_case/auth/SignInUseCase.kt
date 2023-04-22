package com.example.core.domain.use_case.auth

import com.example.core.domain.model.auth.SignInModel
import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.repos.AppRepos
import com.example.core.domain.repos.AuthRepos
import com.example.core.domain.repos.UserRepos
import com.example.core.domain.utils.JWT

class SignInUseCase(
    private val authRepos: AuthRepos,
    private val appRepos: AppRepos,
    private val userRepos: UserRepos,
) {
    suspend fun execute(signInModel: SignInModel) = runCatching {
        val tokenModel = authRepos.signIn(signInModel)
        appRepos.setAccessToken(tokenModel.accessToken)
        appRepos.setRefreshToken(tokenModel.refreshToken)
        val token = tokenModel.accessToken.split(' ')[1]
        val map = JWT(token).getMap(1)
            ?: return@runCatching userRepos.getSingleUser().role
        val role = map["http://schemas.microsoft.com/ws/2008/06/identity/claims/role"]
        RoleLevel.valueOf(role.toString())
    }
}