package com.example.core.di

import com.example.core.data.data_soure.api.AdminApi
import com.example.core.data.data_soure.api.AuthApi
import com.example.core.data.data_soure.api.UserApi
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.new

internal val apiModule by DI.Module {
    bindProvider { AuthApi(instance(baseClient)) }
    bindProvider { UserApi(instance(authorizedClient)) }
    bindProvider { AdminApi(instance(authorizedClient)) }
}