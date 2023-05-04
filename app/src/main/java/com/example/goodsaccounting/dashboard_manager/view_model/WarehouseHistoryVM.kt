package com.example.goodsaccounting.dashboard_manager.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.repos.WarehouseRepos
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.dashboard_manager.model.warehouse_history.WarehouseHistoryEvent
import com.example.goodsaccounting.dashboard_manager.model.warehouse_history.WarehouseHistorySideEffect
import com.example.goodsaccounting.dashboard_manager.model.warehouse_history.WarehouseHistoryState
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class WarehouseHistoryVM (
    override val di: DI
): AppVM<WarehouseHistoryState, WarehouseHistorySideEffect, WarehouseHistoryEvent>(), DIAware{

    private val warehouseRepos : WarehouseRepos by di.instance()
    override val container: Container<WarehouseHistoryState, WarehouseHistorySideEffect> =
        viewModelScope.container(WarehouseHistoryState())
    override fun onError(error: Throwable) {
        Log.e("WarehouseHistoryVM", error.message.toString())
    }



    override fun onEvent(event: WarehouseHistoryEvent) {
       when(event){
           WarehouseHistoryEvent.Refresh -> load()
       }
    }

    private fun load() = intent {
        reduce {
            state.copy(
                isRefreshing = true
            )
        }
        runCatching {
            warehouseRepos.getReceiptMaterial()
        }.onSuccess { list->
            reduce {
                state.copy(
                    isRefreshing = false,
                    receiptMaterial = list
                )
            }
        }.onFailure(::onError)
        runCatching {
            warehouseRepos.getWriteOffMaterial()
        }.onSuccess {list->
            reduce {
                state.copy(
                    isRefreshing = false,
                    writeOffMaterial = list
                )
            }
        }.onFailure(::onError)
    }
}