package com.example.core.di

import com.example.core.domain.use_case.admin.GetUsersUseCase
import com.example.core.domain.use_case.auth.RestoreSessionUseCase
import com.example.core.domain.use_case.auth.SignInUseCase
import com.example.core.domain.use_case.auth.SignUpUseCase
import com.example.core.domain.use_case.user.UpdateUserDataUseCase
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.new

internal val useCaseModule by DI.Module{
    bindSingleton { new(::RestoreSessionUseCase) }
    bindProvider { new(::SignInUseCase) }
    bindProvider { new(::SignUpUseCase) }
    bindProvider { new(::UpdateUserDataUseCase) }
    bindProvider { new(::GetUsersUseCase) }
}