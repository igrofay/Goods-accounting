package com.example.goodsaccounting.dashboard_manager.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.repos.WarehouseRepos
import com.example.core.domain.use_case.warehouse.GetFilteredListUseCase
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.dashboard_manager.model.warehouse.WarehouseEvent
import com.example.goodsaccounting.dashboard_manager.model.warehouse.WarehouseSideEffect
import com.example.goodsaccounting.dashboard_manager.model.warehouse.WarehouseState
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class WarehouseVM(
    override val di: DI
) : AppVM<WarehouseState, WarehouseSideEffect, WarehouseEvent>(), DIAware{
    private val getFilteredListUseCase : GetFilteredListUseCase by di.instance()
    override val container: Container<WarehouseState, WarehouseSideEffect> =
        viewModelScope.container(WarehouseState()){ load() }

    override fun onEvent(event: WarehouseEvent) {
        when(event){
            is WarehouseEvent.SelectFilter -> intent {
                reduce { state.copy(
                    filterListMaterial = event.filterListMaterial,
                    isRefreshing = true,
                ) }
                load()
            }

            WarehouseEvent.Refresh -> load()
        }
    }
    override fun onError(error: Throwable) =intent {
        reduce {
            state.copy(
                isRefreshing = false
            )
        }
        Log.e("WarehouseVM", error.message.toString())
    }
    private fun load() = intent {
        getFilteredListUseCase.execute(state.filterListMaterial)
            .onSuccess { list->
                reduce {
                    state.copy(
                        listAmountOfMaterial = list,
                        isRefreshing = false,
                    )
                }
            }
            .onFailure(::onError)
    }
}