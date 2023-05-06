package com.example.goodsaccounting.create.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.use_case.create.CreateMaterialUseCase
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.create.model.material.CreateMaterialEvent
import com.example.goodsaccounting.create.model.material.CreateMaterialSideEffect
import com.example.goodsaccounting.create.model.material.CreateMaterialState
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

internal class CreateMaterialVM(
    override val di: DI
) : AppVM<CreateMaterialState, CreateMaterialSideEffect, CreateMaterialEvent>(), DIAware {

    private val createMaterialUseCase: CreateMaterialUseCase by di.instance()

    override val container: Container<CreateMaterialState, CreateMaterialSideEffect> =
        viewModelScope.container(CreateMaterialState())


    @OptIn(OrbitExperimental::class)
    override fun onEvent(event: CreateMaterialEvent) {
        when (event) {
            is CreateMaterialEvent.SelectImage -> intent {
                reduce {
                    state.copy(
                        imageUrl = event.uri,
                    )
                }
            }

            is CreateMaterialEvent.InputName -> blockingIntent {
                reduce {
                    state.copy(
                        name = event.name,
                        isErrorName = false,
                    )
                }
            }

            is CreateMaterialEvent.SelectMeasurements -> intent {
                reduce {
                    state.copy(
                        measurement = event.measurements
                    )
                }
            }

            CreateMaterialEvent.Create -> create()
            is CreateMaterialEvent.InputStringMinimumQuantity -> blockingIntent {
                reduce { state.copy(stringMinimumQuantity = event.minimumQuantity) }
            }
        }
    }


    private fun create() = intent {
        val isErrorName = state.name.isBlank()
        val isErrorMinimumQuantity =  state.minimumQuantity == 0f
        if (isErrorName || isErrorMinimumQuantity) {
            reduce {
                state.copy(
                    isErrorName = true,
                    isErrorMinimumQuantity = isErrorMinimumQuantity,
                )
            }
        } else {
            reduce {
                state.copy(
                    isCreating = true
                )
            }
            createMaterialUseCase.execute(state, state.imageUrl)
                .onSuccess {
                    postSideEffect(CreateMaterialSideEffect.MaterialCreated)
                }.onFailure {
                    reduce { state.copy(isCreating = false) }
                    onError(it)
                }
        }
    }

    override fun onError(error: Throwable) {
        Log.e("CreateMaterialVM", error.message.toString())
    }

}