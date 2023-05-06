package com.example.core.domain.use_case.auth

import com.example.core.BuildConfig
import com.example.core.domain.model.error.AuthError
import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.repos.AppRepos
import com.example.core.domain.repos.AuthRepos
import com.example.core.domain.repos.UserRepos
import com.example.core.domain.utils.JWT

class RestoreSessionUseCase(
    private val authRepos: AuthRepos,
    private val appRepos: AppRepos,
    private val userRepos: UserRepos,
) {
    suspend fun execute() = runCatching{
        try {
//            if (BuildConfig.DEBUG) return@runCatching RoleLevel.Seller
            val refreshToken = appRepos.getRefreshToken() ?: throw AuthError.NullRefreshToken
            val tokenModel = authRepos.restoreSession(refreshToken)
            appRepos.setAccessToken(tokenModel.accessToken)
            appRepos.setRefreshToken(tokenModel.refreshToken)
            val accessToken = tokenModel.accessToken.split(' ')[1]
            val map = JWT(accessToken).getMap(1)
                ?: return@runCatching userRepos.getUser().role
            val role = map["http://schemas.microsoft.com/ws/2008/06/identity/claims/role"]
            RoleLevel.valueOf(role.toString())
        }catch (e: AuthError.FailedToRestoreSession){
            appRepos.reset()
            throw e
        }

    }
}