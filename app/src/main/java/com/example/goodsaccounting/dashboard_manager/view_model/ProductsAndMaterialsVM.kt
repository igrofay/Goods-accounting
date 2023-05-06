package com.example.goodsaccounting.dashboard_manager.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.product.AmountOfMaterialModel
import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.model.product.Measurements
import com.example.core.domain.model.product.ProductModel
import com.example.core.domain.repos.ProductsAndMaterialsRepos
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.dashboard_manager.model.products_and_materials.ProductsAndMaterialsEvent
import com.example.goodsaccounting.dashboard_manager.model.products_and_materials.ProductsAndMaterialsSideEffect
import com.example.goodsaccounting.dashboard_manager.model.products_and_materials.ProductsAndMaterialsState
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import java.util.UUID

internal class ProductsAndMaterialsVM(
    override val di: DI,
) : DIAware,
    AppVM<ProductsAndMaterialsState, ProductsAndMaterialsSideEffect, ProductsAndMaterialsEvent>() {

    private val productsAndMaterialsRepos: ProductsAndMaterialsRepos by di.instance()

    override fun onError(error: Throwable) {
        Log.e("ProductsAndMaterialsVM", error.message.toString())
    }

    override val container: Container<ProductsAndMaterialsState, ProductsAndMaterialsSideEffect> =
        viewModelScope.container(ProductsAndMaterialsState())

    override fun onEvent(event: ProductsAndMaterialsEvent) {
        when(event){
            ProductsAndMaterialsEvent.Refresh -> intent {
                reduce { state.copy(isRefreshing = true) }
                load()
            }
        }
    }

    private fun load()= intent {
        runCatching {
            productsAndMaterialsRepos.getMaterials()
        }.onSuccess { list->
            reduce {
                state.copy(
                    listMaterialModel = list,
                    isRefreshing = false,
                )
            }
        }.onFailure(::onError)
        runCatching {
            productsAndMaterialsRepos.getProducts()
        }.onSuccess { list->
            reduce {
                state.copy(
                    listProductModel = list,
                    isRefreshing = false,
                )
            }
        }.onFailure(::onError)
    }
}
