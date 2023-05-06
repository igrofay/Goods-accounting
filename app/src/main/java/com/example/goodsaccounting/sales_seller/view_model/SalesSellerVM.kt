package com.example.goodsaccounting.sales_seller.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.sales_seller.model.SalesSellerEvent
import com.example.goodsaccounting.sales_seller.model.SalesSellerSideEffect
import com.example.goodsaccounting.sales_seller.model.SalesSellerState
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container

internal class SalesSellerVM(
    override val di: DI
) : AppVM<SalesSellerState, SalesSellerSideEffect, SalesSellerEvent>(), DIAware{


    override val container: Container<SalesSellerState, SalesSellerSideEffect> =
        viewModelScope.container(SalesSellerState())


    override fun onError(error: Throwable) {
        Log.e("SalesSellerVM", error.message.toString())
    }
    override fun onEvent(event: SalesSellerEvent) {

    }
}