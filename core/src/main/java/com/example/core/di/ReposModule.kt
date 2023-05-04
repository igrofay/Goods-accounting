package com.example.core.di

import com.example.core.data.repos.AdminPanelReposImpl
import com.example.core.data.repos.AppReposImpl
import com.example.core.data.repos.AuthReposImpl
import com.example.core.data.repos.CreateReposImpl
import com.example.core.data.repos.ProductsAndMaterialsReposImpl
import com.example.core.data.repos.UserReposImpl
import com.example.core.data.repos.WarehouseReposImpl
import com.example.core.domain.repos.AdminPanelRepos
import com.example.core.domain.repos.AppRepos
import com.example.core.domain.repos.AuthRepos
import com.example.core.domain.repos.CreateRepos
import com.example.core.domain.repos.ProductsAndMaterialsRepos
import com.example.core.domain.repos.UserRepos
import com.example.core.domain.repos.WarehouseRepos
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.new

internal val reposModule by DI.Module {
    bindSingleton<AppRepos> { new(::AppReposImpl) }
    bindProvider<AuthRepos> { new(::AuthReposImpl) }
    bindSingleton<UserRepos> { new(::UserReposImpl) }
    bindProvider<AdminPanelRepos> { new(::AdminPanelReposImpl) }
    bindProvider<CreateRepos> { new(::CreateReposImpl) }
    bindProvider<ProductsAndMaterialsRepos> { new(::ProductsAndMaterialsReposImpl) }
    bindProvider<WarehouseRepos>{ new(::WarehouseReposImpl) }
}