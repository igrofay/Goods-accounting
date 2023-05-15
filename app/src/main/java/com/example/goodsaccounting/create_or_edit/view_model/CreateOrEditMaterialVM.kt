package com.example.goodsaccounting.create_or_edit.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.domain.repos.ProductsAndMaterialsRepos
import com.example.core.domain.use_case.create.CreateMaterialUseCase
import com.example.core.domain.use_case.edit.EditMaterialUseCase
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.create_or_edit.model.common.CreateOrEditState
import com.example.goodsaccounting.create_or_edit.model.material.CreateOrEditMaterialEvent
import com.example.goodsaccounting.create_or_edit.model.material.CreateOrEditMaterialSideEffect
import com.example.goodsaccounting.create_or_edit.model.material.CreateOrEditMaterialState
import com.example.goodsaccounting.create_or_edit.model.material.CreateOrEditMaterialState.CreateOrEdit.Companion.fromModelToCreateOrEdit
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

internal class CreateOrEditMaterialVM(
    override val di: DI,
    savedStateHandle: SavedStateHandle,
) : AppVM<CreateOrEditMaterialState, CreateOrEditMaterialSideEffect, CreateOrEditMaterialEvent>(),
    DIAware {

    private val createMaterialUseCase: CreateMaterialUseCase by di.instance()
    private val editMaterialUseCase: EditMaterialUseCase by di.instance()
    private val idMaterial = savedStateHandle.get<String>("idMaterial")
    override val container: Container<CreateOrEditMaterialState, CreateOrEditMaterialSideEffect> =
        viewModelScope.container(CreateOrEditMaterialState.Load) { initState() }

    private val productsAndMaterialsRepos: ProductsAndMaterialsRepos by di.instance()

    @OptIn(OrbitExperimental::class)
    override fun onEvent(event: CreateOrEditMaterialEvent) {
        when (event) {
            is CreateOrEditMaterialEvent.SelectImage -> intent {
                reduce {
                    (state as CreateOrEditMaterialState.CreateOrEdit)
                        .copy(
                            imageUrl = event.uri,
                        )
                }
            }

            is CreateOrEditMaterialEvent.InputName -> blockingIntent {
                reduce {
                    (state as CreateOrEditMaterialState.CreateOrEdit).copy(
                        name = event.name,
                        isErrorName = false,
                    )
                }
            }

            is CreateOrEditMaterialEvent.SelectMeasurements -> intent {
                reduce {
                    (state as CreateOrEditMaterialState.CreateOrEdit).copy(
                        measurement = event.measurements
                    )
                }
            }

            CreateOrEditMaterialEvent.CreateOrEdit -> createOrEdit()

            is CreateOrEditMaterialEvent.InputStringMinimumQuantity -> blockingIntent {
                if (event.minimumQuantity.startsWith("0") || event.minimumQuantity.isBlank()) {
                    reduce {
                        (state as CreateOrEditMaterialState.CreateOrEdit).copy(
                            stringMinimumQuantity = "",
                            isErrorMinimumQuantity = false
                        )
                    }
                } else {
                    event.minimumQuantity.toFloatOrNull() ?: return@blockingIntent
                    reduce {
                        (state as CreateOrEditMaterialState.CreateOrEdit).copy(
                            stringMinimumQuantity = event.minimumQuantity,
                            isErrorMinimumQuantity = false,
                        )
                    }
                }
            }
        }
    }

    private fun createOrEdit() = intent {
        val lastState = (
                state as? CreateOrEditMaterialState.CreateOrEdit
                ) ?: return@intent
        val isErrorName = lastState.name.isBlank()
        val isErrorMinimumQuantity =
            lastState.minimumQuantity == 0f || lastState.minimumQuantity > Float.MAX_VALUE
        if (isErrorName || isErrorMinimumQuantity) {
            reduce {
                lastState.copy(
                    isErrorName = isErrorName,
                    isErrorMinimumQuantity = isErrorMinimumQuantity,
                )
            }
        }else {
            when(lastState.createOrEditState){
                CreateOrEditState.Create ->  create()
                CreateOrEditState.Edit -> edit()
            }
        }
    }
    private fun create() = intent {
        val lastState = state as CreateOrEditMaterialState.CreateOrEdit

        reduce {
            lastState.copy(
                isCreatingOrEdit = true
            )
        }
        createMaterialUseCase.execute(lastState, lastState.imageUrl)
            .onSuccess {
                postSideEffect(CreateOrEditMaterialSideEffect.Created)
            }.onFailure {
                reduce { lastState.copy(isCreatingOrEdit = false) }
                postSideEffect(CreateOrEditMaterialSideEffect.ShowMessage(R.string.network_problems))
                onError(it)
            }
    }

    private fun edit() = intent {
        val lastState = state as CreateOrEditMaterialState.CreateOrEdit
        reduce {
            lastState.copy(
                isCreatingOrEdit = true
            )
        }
        editMaterialUseCase.execute(idMaterial!!, lastState, lastState.imageUrl)
            .onSuccess {
                postSideEffect(CreateOrEditMaterialSideEffect.Edited)
            }.onFailure {
                reduce { lastState.copy(isCreatingOrEdit = false) }
                postSideEffect(CreateOrEditMaterialSideEffect.ShowMessage(R.string.network_problems))
                onError(it)
            }
    }

    override fun onError(error: Throwable) {
        Log.e("CreateMaterialVM", error.message.toString())
    }

    private fun initState() = intent {
        if (idMaterial == null) {
            reduce {
                CreateOrEditMaterialState.CreateOrEdit(
                    CreateOrEditState.Create
                )
            }
        } else {
            runCatching {
                productsAndMaterialsRepos.getMaterial(idMaterial)
            }.onSuccess { model ->
                reduce {
                    model.fromModelToCreateOrEdit()
                }
            }.onFailure {
                onError(it)
            }
        }
    }
}