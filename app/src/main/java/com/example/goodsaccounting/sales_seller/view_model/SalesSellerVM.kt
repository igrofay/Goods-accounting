package com.example.goodsaccounting.sales_seller.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.use_case.seller.GetSalesUseCase
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.sales_seller.model.SalesSellerEvent
import com.example.goodsaccounting.sales_seller.model.SalesSellerSideEffect
import com.example.goodsaccounting.sales_seller.model.SalesSellerState
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class SalesSellerVM(
    override val di: DI
) : AppVM<SalesSellerState, SalesSellerSideEffect, SalesSellerEvent>(), DIAware {
    private val getSalesUseCase: GetSalesUseCase by di.instance()

    override val container: Container<SalesSellerState, SalesSellerSideEffect> =
        viewModelScope.container(SalesSellerState())


    override fun onError(error: Throwable) {
        Log.e("SalesSellerVM", error.message.toString())
    }

    override fun onEvent(event: SalesSellerEvent) {
        when (event) {
            SalesSellerEvent.Refresh -> load()
        }
    }

    private fun load() = intent {
        reduce {
            state.copy(isRefreshing = true)
        }
        getSalesUseCase.execute()
            .onSuccess { list ->
                reduce {
                    state.copy(
                        isRefreshing = false,
                        listSale = list
                    )
                }
            }.onFailure {
                reduce {
                    state.copy(
                        isRefreshing = false
                    )
                }
                onError(it)
            }
    }
}