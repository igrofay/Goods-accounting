package com.example.goodsaccounting.create.view_model

import android.util.Log
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
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class CreateProductVM(
    override val di: DI
) : AppVM<CreateProductState, CreateProductSideEffect, CreateProductEvent>(), DIAware{

    private val createProductUseCase: CreateProductUseCase by di.instance()
    private val productsAndMaterialsRepos: ProductsAndMaterialsRepos by di.instance()

    override fun onError(error: Throwable) {
        Log.e("CreateProductVM", error.message.toString())
    }

    override val container: Container<CreateProductState, CreateProductSideEffect> =
        viewModelScope.container(CreateProductState()){
            loadMaterialsForAdd()
        }

    @OptIn(OrbitExperimental::class)
    override fun onEvent(event: CreateProductEvent) {
        when(event){
            is CreateProductEvent.SelectImage -> intent {
                reduce {
                    state.copy(
                        imageUri = event.imageUri,
                    )
                }
            }

            is CreateProductEvent.InputName -> blockingIntent {
                reduce {
                    state.copy(
                        name = event.name
                    )
                }
            }

            CreateProductEvent.Create -> intent {
                reduce {
                    state.copy(
                        isCreating = true
                    )
                }
            }

            is CreateProductEvent.InputPrice ->blockingIntent {
                reduce {
                    state.copy(
                        textPrice = event.price
                    )
                }
            }

            is CreateProductEvent.SelectCurrency -> intent {
                reduce {
                    state.copy(
                        currency = event.currency
                    )
                }
            }
        }
    }

    private fun loadMaterialsForAdd() = intent {
        runCatching {
            productsAndMaterialsRepos.getMaterials()
        }.onSuccess { materials ->
            reduce {
                state.copy(
                    materialsForAdd = materials.associateBy { it.id }
                )
            }
        }.onFailure(::onError)
    }
}