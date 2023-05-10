package com.example.goodsaccounting.create_or_edit.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.create.AmountOfIdModel
import com.example.core.domain.model.create.CreateProductModel
import com.example.core.domain.use_case.create.CreateProductUseCase
import com.example.core.domain.use_case.product.GetMapIdToMaterialModelUseCase
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.create_or_edit.model.product.CreateProductEvent
import com.example.goodsaccounting.create_or_edit.model.product.CreateProductSideEffect
import com.example.goodsaccounting.create_or_edit.model.product.CreateProductState
import com.example.goodsaccounting.create_or_edit.model.utils.stringToFloat
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

internal class CreateProductVM(
    override val di: DI
) : AppVM<CreateProductState, CreateProductSideEffect, CreateProductEvent>(), DIAware {

    private val createProductUseCase: CreateProductUseCase by di.instance()
    private val getMapIdToMaterialModelUseCase: GetMapIdToMaterialModelUseCase by di.instance()

    override fun onError(error: Throwable) = intent {
        postSideEffect(CreateProductSideEffect.Message(R.string.network_problems))
        Log.e("CreateProductVM", error.message.toString())
    }

    override val container: Container<CreateProductState, CreateProductSideEffect> =
        viewModelScope.container(CreateProductState()) {
            loadMaterialsForAdd()
        }

    @OptIn(OrbitExperimental::class)
    override fun onEvent(event: CreateProductEvent) {
        when (event) {
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
                        name = event.name,
                        isErrorName = false,
                    )
                }
            }

            CreateProductEvent.Create -> create()

            is CreateProductEvent.InputPrice -> blockingIntent {
                reduce {
                    state.copy(
                        textPrice = event.price,
                        isErrorPrice = false,
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

            is CreateProductEvent.AddMaterial -> blockingIntent {
                val newMaterials = state.materials.toMutableMap()
                newMaterials[event.id] = ""
                val newIsErrorAmountOfMaterial = state.isErrorAmountOfMaterial.toMutableMap()
                newIsErrorAmountOfMaterial[event.id] = false
                reduce {
                    state.copy(
                        materials = newMaterials,
                        isErrorAmountOfMaterial = newIsErrorAmountOfMaterial
                    )
                }
            }

            is CreateProductEvent.RemoveMaterial -> blockingIntent {
                val newMaterials = state.materials.toMutableMap()
                newMaterials.remove(event.id)
                val newIsErrorAmountOfMaterial = state.isErrorAmountOfMaterial.toMutableMap()
                newIsErrorAmountOfMaterial.remove(event.id)
                reduce {
                    state.copy(
                        materials = newMaterials,
                        isErrorAmountOfMaterial = newIsErrorAmountOfMaterial,
                    )
                }
            }

            is CreateProductEvent.InputAmountMaterial -> blockingIntent {
                val newMaterials = state.materials.toMutableMap()
                newMaterials[event.id] = event.amount
                val isErrorAmountOfMaterial = state.isErrorAmountOfMaterial.toMutableMap()
                isErrorAmountOfMaterial[event.id] = false
                reduce {
                    state.copy(
                        materials = newMaterials,
                        isErrorAmountOfMaterial = isErrorAmountOfMaterial
                    )
                }
            }
        }
    }

    private fun loadMaterialsForAdd() = intent {
        getMapIdToMaterialModelUseCase.execute()
            .onSuccess { map ->
                reduce {
                    state.copy(
                        materialsForAdd = map,
                    )
                }
            }.onFailure(::onError)
    }


    private fun create() = intent {
        reduce {
            state.copy(
                isCreating = true
            )
        }
        val isErrorName = state.name.isBlank()
        val isErrorPrice = state.textPrice.toFloatOrNull() == null
        val isErrorAmountOfMaterial = state.isErrorAmountOfMaterial.toMutableMap()
        for ((id, amount) in state.materials) {
            isErrorAmountOfMaterial[id] = amount.toFloatOrNull() == null
        }
        val isErrorCommonAmountOfMaterial =
            if (isErrorAmountOfMaterial.values.isEmpty()) false
            else isErrorAmountOfMaterial.values.max()
        if (isErrorName || isErrorPrice || isErrorCommonAmountOfMaterial) {
            reduce {
                state.copy(
                    isCreating = false,
                    isErrorAmountOfMaterial = isErrorAmountOfMaterial,
                    isErrorName = isErrorName,
                    isErrorPrice = isErrorPrice
                )
            }
        } else {
            createProductUseCase.execute(
                createProductModel = object : CreateProductModel {
                    override val name = state.name
                    override val price = stringToFloat(state.textPrice)
                    override val currency = state.currency
                    override val materials = state.materials.map {
                        object : AmountOfIdModel {
                            override val id = it.key
                            override val amount = stringToFloat(it.value)
                        }
                    }

                },
                imageUri = state.imageUri
            ).onSuccess {
                postSideEffect(CreateProductSideEffect.ProductCreated)
            }.onFailure {
                reduce {
                    state.copy(
                        isCreating = false,
                    )
                }
                onError(it)
            }
        }
    }
}