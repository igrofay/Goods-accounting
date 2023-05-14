package com.example.core.di

import com.example.core.data.repos.AdminPanelReposImpl
import com.example.core.data.repos.AnalyticsReposImpl
import com.example.core.data.repos.AppReposImpl
import com.example.core.data.repos.AuthReposImpl
import com.example.core.data.repos.CreateReposImpl
import com.example.core.data.repos.EditReposImpl
import com.example.core.data.repos.ProductsAndMaterialsReposImpl
import com.example.core.data.repos.RestorePasswordReposImpl
import com.example.core.data.repos.SellerReposImpl
import com.example.core.data.repos.UserReposImpl
import com.example.core.data.repos.WarehouseReposImpl
import com.example.core.domain.repos.AdminPanelRepos
import com.example.core.domain.repos.AnalyticsRepos
import com.example.core.domain.repos.AppRepos
import com.example.core.domain.repos.AuthRepos
import com.example.core.domain.repos.CreateRepos
import com.example.core.domain.repos.EditRepos
import com.example.core.domain.repos.ProductsAndMaterialsRepos
import com.example.core.domain.repos.RestorePasswordRepos
import com.example.core.domain.repos.SellerRepos
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
    bindProvider<AnalyticsRepos> { new(::AnalyticsReposImpl) }
    bindProvider<SellerRepos> { new(::SellerReposImpl) }
    bindProvider<EditRepos> { new(::EditReposImpl) }
    bindProvider<RestorePasswordRepos> { new(::RestorePasswordReposImpl) }
}