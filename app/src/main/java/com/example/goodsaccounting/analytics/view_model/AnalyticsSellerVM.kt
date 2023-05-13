package com.example.goodsaccounting.analytics.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.use_case.analytics.GetSellerIncomeUseCase
import com.example.goodsaccounting.analytics.model.seller.AnalyticsSellerEvent
import com.example.goodsaccounting.analytics.model.seller.AnalyticsSellerSideEffect
import com.example.goodsaccounting.analytics.model.seller.AnalyticsSellerState
import com.example.goodsaccounting.analytics.model.seller.AnalyticsSellerState.AnalyticsData.Companion.fromModelToAnalyticsData
import com.example.goodsaccounting.common.view_model.AppVM
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class AnalyticsSellerVM(
    override val di: DI
) : AppVM<AnalyticsSellerState,AnalyticsSellerSideEffect, AnalyticsSellerEvent >(), DIAware{

    private val getSellerIncomeUseCase : GetSellerIncomeUseCase by di.instance()

    override val container: Container<AnalyticsSellerState, AnalyticsSellerSideEffect> =
        viewModelScope.container(AnalyticsSellerState.Loading)

    override fun onError(error: Throwable) {
        Log.e("AnalyticsSellerVM", error.message.toString())
    }

    override fun onEvent(event: AnalyticsSellerEvent) {
        when(event){
            AnalyticsSellerEvent.Refresh -> load()
        }
    }

    private fun load() = intent {
        reduce {
            (state as? AnalyticsSellerState.AnalyticsData)?.copy(
                isRefreshing = true
            ) ?: state
        }
        getSellerIncomeUseCase.execute()
            .onSuccess { sellerIncome->
               reduce {
                   sellerIncome.fromModelToAnalyticsData()
               }
            }
            .onFailure {
                reduce {
                    (state as? AnalyticsSellerState.AnalyticsData)?.copy(
                        isRefreshing = false
                    ) ?: state
                }
                onError(it)
            }
    }

}