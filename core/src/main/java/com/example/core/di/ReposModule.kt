package com.example.core.di

import com.example.core.data.repos.AdminReposImpl
import com.example.core.data.repos.AppReposImpl
import com.example.core.data.repos.AuthReposImpl
import com.example.core.data.repos.UserReposImpl
import com.example.core.domain.repos.AdminRepos
import com.example.core.domain.repos.AppRepos
import com.example.core.domain.repos.AuthRepos
import com.example.core.domain.repos.UserRepos
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.new

internal val reposModule by DI.Module{
    bindSingleton<AppRepos>{ new(::AppReposImpl) }
    bindProvider<AuthRepos> { new(::AuthReposImpl) }
    bindSingleton<UserRepos> { new(::UserReposImpl) }
    bindProvider<AdminRepos>{ new(::AdminReposImpl) }

}