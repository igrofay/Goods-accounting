package com.example.goodsaccounting.create.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.create.AmountOfIdModel
import com.example.core.domain.model.create.CreateReceiptOrWriteOffMaterialModel
import com.example.core.domain.use_case.create.CreateReceiptOrWriteOffMaterialUseCase
import com.example.core.domain.use_case.material.GetMapIdToMaterialModelUseCase
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.create.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialEvent
import com.example.goodsaccounting.create.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialSideEffect
import com.example.goodsaccounting.create.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialState
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

internal class CreateReceiptOrWriteOfMaterialVM(
    handle: SavedStateHandle,
    override val di: DI,
) : AppVM<CreateReceiptOrWriteOfMaterialState, CreateReceiptOrWriteOfMaterialSideEffect, CreateReceiptOrWriteOfMaterialEvent>(),
    DIAware {
    private val getMapIdToMaterialModelUseCase: GetMapIdToMaterialModelUseCase by di.instance()
    private val createReceiptOrWriteOffMaterialUseCase: CreateReceiptOrWriteOffMaterialUseCase by di.instance()
    override fun onError(error: Throwable) {
        Log.e("CreateReceiptOrWriteOfMaterialVM", error.message.toString())
    }

    override val container: Container<CreateReceiptOrWriteOfMaterialState, CreateReceiptOrWriteOfMaterialSideEffect> =
        viewModelScope.container(
            CreateReceiptOrWriteOfMaterialState(
                isReceipt = handle.get<Boolean>("isReceipt") == true
            )
        ){
            load()
        }

    @OptIn(OrbitExperimental::class)
    override fun onEvent(event: CreateReceiptOrWriteOfMaterialEvent) {
        when (event) {
            CreateReceiptOrWriteOfMaterialEvent.Create -> create()
            is CreateReceiptOrWriteOfMaterialEvent.SelectImage -> intent {
                val newListImage = state.listImageUri.toMutableList()
                if (event.position < newListImage.size) {
                    newListImage[event.position] = event.imageUri
                } else {
                    newListImage.add(event.imageUri)
                }
                reduce {
                    state.copy(
                        listImageUri = newListImage
                    )
                }
            }

            is CreateReceiptOrWriteOfMaterialEvent.InputName -> blockingIntent {
                reduce {
                    state.copy(
                        name = event.name
                    )
                }
            }

            is CreateReceiptOrWriteOfMaterialEvent.AddMaterial ->  blockingIntent {
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
            is CreateReceiptOrWriteOfMaterialEvent.RemoveMaterial -> blockingIntent {
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

            is CreateReceiptOrWriteOfMaterialEvent.InputAmountMaterial -> blockingIntent {
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

    private fun load() = intent {
        getMapIdToMaterialModelUseCase.execute()
            .onSuccess { map ->
                reduce {
                    state.copy(
                        materialsForAddInList = map
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
        val isErrorListImage = state.listImageUri.isEmpty()
        val isErrorName = state.name.isBlank()
        val isErrorAmountOfMaterial = state.isErrorAmountOfMaterial.toMutableMap()
        for ((id, amount) in state.materials) {
            isErrorAmountOfMaterial[id] = amount.toFloatOrNull() == null
        }
        val isErrorCommonAmountOfMaterial =
            if (isErrorAmountOfMaterial.values.isEmpty()) true
            else isErrorAmountOfMaterial.values.max()
        if (isErrorName || isErrorCommonAmountOfMaterial || isErrorListImage) {
            reduce {
                state.copy(
                    isCreating = false,
                    isErrorAmountOfMaterial = isErrorAmountOfMaterial,
                    isErrorName = isErrorName,
                )
            }
            if(isErrorListImage)
                postSideEffect(CreateReceiptOrWriteOfMaterialSideEffect.Message(R.string.add_photos))
            if (isErrorAmountOfMaterial.values.isEmpty())
                postSideEffect(CreateReceiptOrWriteOfMaterialSideEffect.Message(R.string.add_materials))
        }else{
            createReceiptOrWriteOffMaterialUseCase.execute(
                isReceipt = state.isReceipt,
                listImageUri = state.listImageUri,
                createReceiptOrWriteOffMaterialModel = object : CreateReceiptOrWriteOffMaterialModel{
                    override val name = state.name
                    override val listAmountOfMaterial = state.materials.map {
                        object : AmountOfIdModel{
                            override val id = it.key
                            override val amount = stringToFloat(it.value)
                        }
                    }
                }
            ).onSuccess {
                postSideEffect(CreateReceiptOrWriteOfMaterialSideEffect.Created)
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
    private fun stringToFloat(text: String): Float {
        return if (text.length > 2) {
            val intPart = text
                .dropLast(2)
            val fractionPart = text
                .takeLast(2)
            "$intPart.$fractionPart".toFloat()
        } else {
            "0.$text".toFloat()
        }
    }
}