package com.example.goodsaccounting.analytics.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.analytics.SellerIncomeModel
import com.example.core.domain.model.product.AmountOfMaterialModel
import com.example.core.domain.model.product.AmountOfProductModel
import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.ProductModel
import com.example.core.domain.use_case.analytics.GetManagerIncomeUseCase
import com.example.goodsaccounting.analytics.model.common.AmountOfProductCartEntry.Companion.fromListModelToChartEntryModel
import com.example.goodsaccounting.analytics.model.common.SellerIncomeCartEntry.Companion.fromListModelToChartEntryModel
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerEvent
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerSideEffect
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerState
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerState.AnalyticsData.Companion.fromModelToAnalyticsData
import com.example.goodsaccounting.common.view_model.AppVM
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class AnalyticsManagerVM(
    override val di: DI
) : AppVM<AnalyticsManagerState, AnalyticsManagerSideEffect, AnalyticsManagerEvent>(), DIAware{

    private val getManagerIncomeUseCase: GetManagerIncomeUseCase by di.instance()

    override val container: Container<AnalyticsManagerState, AnalyticsManagerSideEffect> =
        viewModelScope.container(AnalyticsManagerState.Load)

    override fun onEvent(event: AnalyticsManagerEvent) {
        when(event){
            AnalyticsManagerEvent.Refresh -> load()
        }
    }

    override fun onError(error: Throwable) {
        Log.e("AnalyticsManagerVM", error.message.toString())
    }

    private fun load() = intent {
        reduce {
            (state as? AnalyticsManagerState.AnalyticsData)
                ?.copy(isRefreshing = true) ?: state
        }
        getManagerIncomeUseCase.execute().onSuccess { model->
            reduce {
                model.fromModelToAnalyticsData()
            }
        }.onFailure {
            reduce {
                (state as? AnalyticsManagerState.AnalyticsData)
                    ?.copy(isRefreshing = false) ?: state
            }
            onError(it)
        }
    }
}