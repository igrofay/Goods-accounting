package com.example.core.di

import com.example.core.data.data_soure.database.TokenDatabase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.new

internal val databaseModule by DI.Module{
    bindSingleton { new(::TokenDatabase) }
}