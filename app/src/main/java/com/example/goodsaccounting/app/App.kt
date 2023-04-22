package com.example.goodsaccounting.app

import android.app.Application
import com.example.core.di.coreModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindProvider

internal class App : Application(), DIAware {
    override val di by DI.lazy {
        bindProvider { applicationContext }
        import(coreModule)
    }
}