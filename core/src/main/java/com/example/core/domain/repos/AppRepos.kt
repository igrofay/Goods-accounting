package com.example.core.domain.repos

interface AppRepos {
    fun getAccessToken() : String?
    fun setAccessToken(token : String)
    fun getRefreshToken() : String?
    fun setRefreshToken(token:String)
    fun reset()
}