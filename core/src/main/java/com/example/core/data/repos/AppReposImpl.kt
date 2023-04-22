package com.example.core.data.repos

import android.os.Build
import com.example.core.data.data_soure.database.TokenDatabase
import com.example.core.data.utils.VernamCipher
import com.example.core.data.utils.toSha256
import com.example.core.domain.repos.AppRepos

internal class AppReposImpl(
    private val tokenDatabase: TokenDatabase
) : AppRepos {
    private val cipher = VernamCipher()

    private val aValueForAccess = Build.ID.toSha256().toByteArray().sum()
    private val cValueForAccess = "Access".toSha256().toByteArray().sum()

    override fun getAccessToken(): String? {
        val token = tokenDatabase.accessToken ?: return null
        val key = cipher.generateKey(
            token.length,
            500,
            aValueForAccess,
            cValueForAccess,
            Char.MAX_VALUE.code / 2
        )
        return cipher.decipher(token, key)
    }

    override fun setAccessToken(token: String) {
        val key = cipher.generateKey(
            token.length,
            500,
            aValueForAccess,
            cValueForAccess,
            Char.MAX_VALUE.code / 2
        )
        tokenDatabase.accessToken = cipher.encrypt(token, key)
    }

    private val aValueForRefresh = Build.MODEL.toSha256().toByteArray().sum()
    private val cValueForRefresh = "Refresh".toSha256().toByteArray().sum()

    override fun getRefreshToken(): String? {
        val token = tokenDatabase.refreshToken ?: return null
        val key = cipher.generateKey(
            token.length,
            40,
            aValueForRefresh,
            cValueForRefresh,
            Char.MAX_VALUE.code / 2
        )
        return cipher.decipher(token, key)
    }

    override fun setRefreshToken(token: String) {
        val key = cipher.generateKey(
            token.length,
            40,
            aValueForRefresh,
            cValueForRefresh,
            Char.MAX_VALUE.code / 2
        )
        tokenDatabase.refreshToken = cipher.encrypt(token, key)
    }

    override fun reset() {
        tokenDatabase.refreshToken = null
        tokenDatabase.accessToken = null
    }
}