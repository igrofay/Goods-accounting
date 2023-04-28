package com.example.core.di

import com.example.core.domain.use_case.admin.GetUsersUseCase
import com.example.core.domain.use_case.auth.RestoreSessionUseCase
import com.example.core.domain.use_case.auth.SignInUseCase
import com.example.core.domain.use_case.auth.SignUpUseCase
import com.example.core.domain.use_case.create.CreateMaterialUseCase
import com.example.core.domain.use_case.create.CreateProductUseCase
import com.example.core.domain.use_case.user.GetUserFlowUseCase
import com.example.core.domain.use_case.user.UpdateUserDataUseCase
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.new

internal val useCaseModule by DI.Module{
    // auth
    bindSingleton { new(::RestoreSessionUseCase) }
    bindProvider { new(::SignInUseCase) }
    bindProvider { new(::SignUpUseCase) }
    // user
    bindProvider { new(::UpdateUserDataUseCase) }
    bindProvider { new(::GetUserFlowUseCase) }
    // admin
    bindProvider { new(::GetUsersUseCase) }
    // manager
    bindProvider { new(::CreateMaterialUseCase) }
    bindProvider { new(::CreateProductUseCase) }
}