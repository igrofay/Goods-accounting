package com.example.core.di

import org.kodein.di.DI

val coreModule by DI.Module{
    import(dataSourceModule)
    import(apiModule)
    import(databaseModule)
    import(networkMode)
    import(reposModule)
    import(useCaseModule)
}