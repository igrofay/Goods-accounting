package com.example.goodsaccounting.create_or_edit.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.create_or_edit.AmountOfIdModel
import com.example.core.domain.model.create_or_edit.CreateOrEditProductModel
import com.example.core.domain.repos.ProductsAndMaterialsRepos
import com.example.core.domain.use_case.create.CreateProductUseCase
import com.example.core.domain.use_case.edit.EditProductUseCase
import com.example.core.domain.use_case.product.GetMapIdToMaterialModelUseCase
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.create_or_edit.model.common.CreateOrEditState
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductEvent
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductSideEffect
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductState
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductState.CreateOrEdit.Companion.fromModelToCreateOrEdit
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

internal class CreateOrEditProductVM(
    override val di: DI,
    savedStateHandle: SavedStateHandle
) : AppVM<CreateOrEditProductState, CreateOrEditProductSideEffect, CreateOrEditProductEvent>(), DIAware {
    private val idProduct = savedStateHandle.get<String>("idProduct")
    private val createProductUseCase: CreateProductUseCase by di.instance()
    private val editProductUseCase : EditProductUseCase by di.instance()
    private val getMapIdToMaterialModelUseCase: GetMapIdToMaterialModelUseCase by di.instance()
    private val productsAndMaterialsRepos : ProductsAndMaterialsRepos by di.instance()

    override fun onError(error: Throwable) = intent {
        postSideEffect(CreateOrEditProductSideEffect.Message(R.string.network_problems))
        Log.e("CreateProductVM", error.message.toString())
    }

    override val container: Container<CreateOrEditProductState, CreateOrEditProductSideEffect> =
        viewModelScope.container(CreateOrEditProductState.Load) { initState() }

    @OptIn(OrbitExperimental::class)
    override fun onEvent(event: CreateOrEditProductEvent) {
        when (event) {
            is CreateOrEditProductEvent.SelectImage -> intent {
                reduce {
                    (state as CreateOrEditProductState.CreateOrEdit).copy(
                        imageUri = event.imageUri,
                    )
                }
            }

            is CreateOrEditProductEvent.InputName -> blockingIntent {
                reduce {
                    (state as? CreateOrEditProductState.CreateOrEdit)?.copy(
                        name = event.name,
                        isErrorName = false,
                    ) ?: state
                }
            }

            CreateOrEditProductEvent.CreateOrEdit -> createOrEdit()
            is CreateOrEditProductEvent.InputPrice -> blockingIntent {
                reduce {
                    (state as? CreateOrEditProductState.CreateOrEdit)?.copy(
                        textPrice = event.price,
                        isErrorPrice = false,
                    ) ?: state
                }
            }

            is CreateOrEditProductEvent.SelectCurrency -> intent {
                reduce {
                    (state as?  CreateOrEditProductState.CreateOrEdit)?.copy(
                        currency = event.currency
                    ) ?: state
                }
            }

            is CreateOrEditProductEvent.AddMaterial -> blockingIntent {
                val lastState =
                    (state as? CreateOrEditProductState.CreateOrEdit)
                        ?: return@blockingIntent
                val newMaterials = lastState.materials.toMutableMap()
                newMaterials[event.id] = ""
                val newIsErrorAmountOfMaterial = lastState.isErrorAmountOfMaterial.toMutableMap()
                newIsErrorAmountOfMaterial[event.id] = false
                reduce {
                    lastState.copy(
                        materials = newMaterials,
                        isErrorAmountOfMaterial = newIsErrorAmountOfMaterial
                    )
                }
            }

            is CreateOrEditProductEvent.RemoveMaterial -> blockingIntent {
                val lastState =
                    (state as? CreateOrEditProductState.CreateOrEdit)
                        ?: return@blockingIntent
                val newMaterials = lastState.materials.toMutableMap()
                newMaterials.remove(event.id)
                val newIsErrorAmountOfMaterial = lastState.isErrorAmountOfMaterial.toMutableMap()
                newIsErrorAmountOfMaterial.remove(event.id)
                reduce {
                    lastState.copy(
                        materials = newMaterials,
                        isErrorAmountOfMaterial = newIsErrorAmountOfMaterial,
                    )
                }
            }

            is CreateOrEditProductEvent.InputAmountMaterial -> blockingIntent {
                val lastState =
                    (state as? CreateOrEditProductState.CreateOrEdit)
                        ?: return@blockingIntent
                val newMaterials = lastState.materials.toMutableMap()
                newMaterials[event.id] = event.amount
                val isErrorAmountOfMaterial = lastState.isErrorAmountOfMaterial.toMutableMap()
                isErrorAmountOfMaterial[event.id] = false
                reduce {
                    lastState.copy(
                        materials = newMaterials,
                        isErrorAmountOfMaterial = isErrorAmountOfMaterial
                    )
                }
            }
        }
    }

    private fun createOrEdit() = intent {
        val lastState =
            (state as? CreateOrEditProductState.CreateOrEdit)
                ?: return@intent
        reduce {
            lastState.copy(
                isCreating = true
            )
        }
        val isErrorName = lastState.name.isBlank()
        val isErrorPrice = lastState.textPrice.toFloatOrNull() == null
        val isErrorAmountOfMaterial = lastState.isErrorAmountOfMaterial.toMutableMap()
        for ((id, amount) in lastState.materials) {
            isErrorAmountOfMaterial[id] = amount.toFloatOrNull() == null
        }
        val isErrorCommonAmountOfMaterial =
            if (isErrorAmountOfMaterial.values.isEmpty()) false
            else isErrorAmountOfMaterial.values.max()
        if (isErrorName || isErrorPrice || isErrorCommonAmountOfMaterial) {
            reduce {
                lastState.copy(
                    isCreating = false,
                    isErrorAmountOfMaterial = isErrorAmountOfMaterial,
                    isErrorName = isErrorName,
                    isErrorPrice = isErrorPrice
                )
            }
        } else {
            when(lastState.createOrEditState){
                CreateOrEditState.Create -> create()
                CreateOrEditState.Edit -> edit()
            }
        }
    }

    private fun create() = intent {
        val lastState = state as CreateOrEditProductState.CreateOrEdit
        createProductUseCase.execute(
            createOrEditProductModel = object : CreateOrEditProductModel {
                override val name = lastState.name
                override val price = stringToFloat(lastState.textPrice)
                override val currency = lastState.currency
                override val materials = lastState.materials.map {
                    object : AmountOfIdModel {
                        override val id = it.key
                        override val amount = stringToFloat(it.value)
                    }
                }

            },
            imageUri = lastState.imageUri
        ).onSuccess {
            postSideEffect(CreateOrEditProductSideEffect.Created)
        }.onFailure {
            reduce {
                (state as? CreateOrEditProductState.CreateOrEdit)?.copy(
                    isCreating = false,
                ) ?: state
            }
            onError(it)
        }
    }
    private fun edit() = intent {
        val lastState = state as CreateOrEditProductState.CreateOrEdit
        editProductUseCase.execute(
            idProduct!!,
            createOrEditProductModel = object : CreateOrEditProductModel {
                override val name = lastState.name
                override val price = stringToFloat(lastState.textPrice)
                override val currency = lastState.currency
                override val materials = lastState.materials.map {
                    object : AmountOfIdModel {
                        override val id = it.key
                        override val amount = stringToFloat(it.value)
                    }
                }

            },
            imageUri = lastState.imageUri
        ).onSuccess {
            postSideEffect(CreateOrEditProductSideEffect.Edited)
        }.onFailure {
            reduce {
                (state as? CreateOrEditProductState.CreateOrEdit)?.copy(
                    isCreating = false,
                ) ?: state
            }
            onError(it)
        }
    }

    private fun initState() = intent {
        if (idProduct == null){
            getMapIdToMaterialModelUseCase.execute()
                .onSuccess { map ->
                    reduce {
                        CreateOrEditProductState.CreateOrEdit(
                            CreateOrEditState.Create,
                            materialsForAdd = map,
                        )
                    }
                }.onFailure(::onError)
        }else{
            runCatching {
                productsAndMaterialsRepos.getProduct(idProduct)
            }.onSuccess { model->
                getMapIdToMaterialModelUseCase.execute()
                    .onSuccess { map ->
                        reduce {
                            model.fromModelToCreateOrEdit(map)
                        }
                    }.onFailure(::onError)
            }.onFailure(::onError)
        }
    }
}