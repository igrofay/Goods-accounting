package com.example.goodsaccounting.create.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.create.AmountOfIdModel
import com.example.core.domain.model.create.CreateSaleModel
import com.example.core.domain.use_case.create.CreateSaleSellerUseCase
import com.example.core.domain.use_case.product.GetMapIdToProductModelUseCase
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.create.model.sale_seller.CreateSaleSellerEvent
import com.example.goodsaccounting.create.model.sale_seller.CreateSaleSellerSideEffect
import com.example.goodsaccounting.create.model.sale_seller.CreateSaleSellerState
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

internal class CreateSaleSellerVM(
    override val di: DI

) : AppVM<CreateSaleSellerState, CreateSaleSellerSideEffect, CreateSaleSellerEvent>(), DIAware {
    private val getMapIdToProductModelUseCase: GetMapIdToProductModelUseCase by di.instance()
    private val createSaleSellerUseCase: CreateSaleSellerUseCase by di.instance()

    override val container: Container<CreateSaleSellerState, CreateSaleSellerSideEffect> =
        viewModelScope.container(CreateSaleSellerState()){ load() }

    @OptIn(OrbitExperimental::class)
    override fun onEvent(event: CreateSaleSellerEvent) {
        when(event){
            is CreateSaleSellerEvent.RemoveImage -> intent {
                val newListImageUri  = state.listImageUri.toMutableList()
                newListImageUri.removeAt(event.position)
                reduce { state.copy(
                    listImageUri = newListImageUri,
                ) }
            }
            is CreateSaleSellerEvent.SelectImage -> intent {
                val newListImageUri  = state.listImageUri.toMutableList()
                if (event.position< newListImageUri.size){
                    newListImageUri[event.position] = event.uri
                }else{
                    newListImageUri.add(event.uri)
                }
                reduce { state.copy(
                    listImageUri = newListImageUri,
                ) }
            }

            is CreateSaleSellerEvent.InputName -> blockingIntent {
                reduce {
                    state.copy(
                        name = event.name
                    )
                }
            }

            CreateSaleSellerEvent.Create -> create()
            is CreateSaleSellerEvent.AddProduct -> blockingIntent {
                val newProducts = state.products.toMutableMap()
                newProducts[event.id] = null
                val newIsErrorAmountOfProduct = state.isErrorAmountOfProduct.toMutableMap()
                newIsErrorAmountOfProduct[event.id] = false
                reduce {
                    state.copy(
                        products = newProducts,
                        isErrorAmountOfProduct = newIsErrorAmountOfProduct
                    )
                }
            }
            is CreateSaleSellerEvent.RemoveProduct -> blockingIntent {
                val newProducts = state.products.toMutableMap()
                newProducts.remove(event.id)
                val newIsErrorAmountOfProduct = state.isErrorAmountOfProduct.toMutableMap()
                newIsErrorAmountOfProduct.remove(event.id)
                reduce {
                    state.copy(
                        products = newProducts,
                        isErrorAmountOfProduct = newIsErrorAmountOfProduct
                    )
                }
            }

            is CreateSaleSellerEvent.InputAmountOfProduct -> intent {
                val amountNum = event.amount.toIntOrNull()
                val newProducts = state.products.toMutableMap()
                newProducts[event.id] = amountNum
                reduce {
                    state.copy(
                        products = newProducts,
                    )
                }
                calculationCheckPrice()
            }

            is CreateSaleSellerEvent.SelectCurrency -> intent {
                reduce { state.copy(currency = event.currency) }
            }
        }
    }

    override fun onError(error: Throwable) {
        Log.e("CreateSaleSellerVM", error.message.toString())
    }

    private fun load() = intent {
        getMapIdToProductModelUseCase.execute()
            .onSuccess {map ->
                reduce { state.copy(
                    listProductForAdd = map
                ) }
            }
            .onFailure(::onError)
    }
    private fun create() = intent {
        reduce {
            state.copy(isCreating = true)
        }
        val isErrorName = state.name.isBlank()
        val isErrorListImage = state.listImageUri.isEmpty()
        val isErrorAmountOfProduct = state.isErrorAmountOfProduct.toMutableMap()
        for ((id, amount) in state.products) {
            isErrorAmountOfProduct[id] = amount == null || amount == 0
        }
        val isErrorCommonAmountOfProduct =
            if (isErrorAmountOfProduct.values.isEmpty()) true
            else isErrorAmountOfProduct.values.max()
        if (
            isErrorName || isErrorListImage || isErrorCommonAmountOfProduct
        ){
            reduce {
                state.copy(
                    isCreating = false,
                    isErrorName = isErrorName,
                    isErrorAmountOfProduct = isErrorAmountOfProduct
                )
            }
            if (isErrorListImage)
                postSideEffect(CreateSaleSellerSideEffect.Message(R.string.add_photos))
            if(isErrorAmountOfProduct.isEmpty())
                postSideEffect(CreateSaleSellerSideEffect.Message(R.string.add_products))
        }else{
            createSaleSellerUseCase.execute(
                createSaleModel = object : CreateSaleModel{
                    override val name = state.name
                    override val products = state.products.map {
                        object : AmountOfIdModel{
                            override val id = it.key
                            override val amount = it.value!!.toFloat()
                        }
                    }
                    override val checkPrice = state.checkPrice
                    override val currency = state.currency
                },
                listImageUri = state.listImageUri
            ).onSuccess {
                postSideEffect(CreateSaleSellerSideEffect.Created)
            }.onFailure {
                reduce {
                    state.copy(isCreating = false)
                }
            }
        }
    }
    private fun calculationCheckPrice() = intent {
        var newCheckPrice = 0f
        for ( item in state.products){
            val amountInt = item.value ?: continue
            newCheckPrice += state.listProductForAdd.getValue(item.key).price * amountInt
        }
        reduce {
            state.copy(
                checkPrice = newCheckPrice
            )
        }
    }
}