package com.example.core.di

import com.example.core.data.data_soure.api.AdminApi
import com.example.core.data.data_soure.api.AnalyticsApi
import com.example.core.data.data_soure.api.AuthApi
import com.example.core.data.data_soure.api.MaterialAccountingApi
import com.example.core.data.data_soure.api.MaterialApi
import com.example.core.data.data_soure.api.ProductApi
import com.example.core.data.data_soure.api.SaleApi
import com.example.core.data.data_soure.api.UserApi
import com.example.core.data.data_soure.api.WarehouseApi
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val apiModule by DI.Module {
    bindProvider { AuthApi(instance(baseClient)) }
    bindProvider { UserApi(instance(authorizedClient)) }
    bindProvider { AdminApi(instance(authorizedClient)) }
    bindProvider { MaterialApi(instance(authorizedClient)) }
    bindProvider { ProductApi(instance(authorizedClient)) }
    bindProvider { MaterialAccountingApi(instance(authorizedClient)) }
    bindProvider { AnalyticsApi(instance(authorizedClient)) }
    bindProvider { SaleApi(instance(authorizedClient)) }
    bindProvider { WarehouseApi(instance(authorizedClient)) }
}