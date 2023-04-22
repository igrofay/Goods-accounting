package com.example.core.di

import android.content.Context
import android.content.SharedPreferences
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private const val keySharedPreferences = "TAG_keySharedPreferences"

internal val dataSourceModule by DI.Module {
    bindSingleton<SharedPreferences> {
        instance<Context>().getSharedPreferences(
            keySharedPreferences,
            Context.MODE_PRIVATE
        )
    }

}



