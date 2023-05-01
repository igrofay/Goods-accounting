package com.example.goodsaccounting.create.view_model

import androidx.lifecycle.viewModelScope
import com.example.core.domain.repos.ProductsAndMaterialsRepos
import com.example.core.domain.use_case.create.CreateProductUseCase
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.create.model.product.CreateProductEvent
import com.example.goodsaccounting.create.model.product.CreateProductSideEffect
import com.example.goodsaccounting.create.model.product.CreateProductState
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class CreateProductVM(
    override val di: DI
) : AppVM<CreateProductState, CreateProductSideEffect, CreateProductEvent>(), DIAware{

    private val createProductUseCase: CreateProductUseCase by di.instance()
    private val productsAndMaterialsRepos: ProductsAndMaterialsRepos by di.instance()

    override fun onError(error: Throwable) {

    }

    override val container: Container<CreateProductState, CreateProductSideEffect> =
        viewModelScope.container(CreateProductState())

    override fun onEvent(event: CreateProductEvent) {
        when(event){
            is CreateProductEvent.SelectImage -> intent {
                reduce {
                    state.copy(
                        imageUri = event.imageUri,
                    )
                }
            }
        }
    }
}