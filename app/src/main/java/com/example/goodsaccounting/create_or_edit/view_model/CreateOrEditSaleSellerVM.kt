package com.example.goodsaccounting.create_or_edit.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.create.AmountOfIdModel
import com.example.core.domain.model.create.CreateOrEditSaleModel
import com.example.core.domain.use_case.create.CreateSaleSellerUseCase
import com.example.core.domain.use_case.edit.EditSaleUseCase
import com.example.core.domain.use_case.product.GetMapIdToProductModelUseCase
import com.example.core.domain.use_case.seller.GetSaleUseCase
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.create_or_edit.model.common.CreateOrEditState
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateOrEditSaleSellerEvent
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateOrEditSaleSellerSideEffect
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateOrEditSaleSellerState
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

internal class CreateOrEditSaleSellerVM(
    override val di: DI,
    private val savedStateHandle: SavedStateHandle,
) : AppVM<CreateOrEditSaleSellerState, CreateOrEditSaleSellerSideEffect, CreateOrEditSaleSellerEvent>(),
    DIAware {
    private val editSaleSellerUseCase: EditSaleUseCase by di.instance()
    private val getMapIdToProductModelUseCase: GetMapIdToProductModelUseCase by di.instance()
    private val createSaleSellerUseCase: CreateSaleSellerUseCase by di.instance()
    private val getSaleUseCase: GetSaleUseCase by di.instance()

    private val idSale = savedStateHandle.get<String>("idSale")
    override val container: Container<CreateOrEditSaleSellerState, CreateOrEditSaleSellerSideEffect> =
        viewModelScope.container(CreateOrEditSaleSellerState.Load) {
            val createOrEditState =
                if (idSale != null) CreateOrEditState.Edit
                else CreateOrEditState.Create
            initState(createOrEditState)
        }

    @OptIn(OrbitExperimental::class)
    override fun onEvent(event: CreateOrEditSaleSellerEvent) {
        when (event) {
            is CreateOrEditSaleSellerEvent.RemoveImage -> intent {
                val lastState =
                    (state as? CreateOrEditSaleSellerState.CreateOrEdit) ?: return@intent
                val newListImageUri = lastState.listImageUri.toMutableList()
                newListImageUri.removeAt(event.position)
                reduce {
                    lastState.copy(
                        listImageUri = newListImageUri,
                    )
                }
            }

            is CreateOrEditSaleSellerEvent.SelectImage -> intent {
                val lastState =
                    (state as? CreateOrEditSaleSellerState.CreateOrEdit) ?: return@intent
                val newListImageUri = lastState.listImageUri.toMutableList()
                if (event.position < newListImageUri.size) {
                    newListImageUri[event.position] = event.uri
                } else {
                    newListImageUri.add(event.uri)
                }
                reduce {
                    lastState.copy(
                        listImageUri = newListImageUri,
                    )
                }
            }

            is CreateOrEditSaleSellerEvent.InputName -> blockingIntent {
                val lastState =
                    (state as? CreateOrEditSaleSellerState.CreateOrEdit) ?: return@blockingIntent
                reduce {
                    lastState.copy(
                        name = event.name
                    )
                }
            }

            CreateOrEditSaleSellerEvent.CreateOrEdit -> create()
            is CreateOrEditSaleSellerEvent.AddProduct -> blockingIntent {
                val lastState =
                    (state as? CreateOrEditSaleSellerState.CreateOrEdit) ?: return@blockingIntent
                val newProducts = lastState.products.toMutableMap()
                newProducts[event.id] = null
                val newIsErrorAmountOfProduct = lastState.isErrorAmountOfProduct.toMutableMap()
                newIsErrorAmountOfProduct[event.id] = false
                reduce {
                    lastState.copy(
                        products = newProducts,
                        isErrorAmountOfProduct = newIsErrorAmountOfProduct
                    )
                }
            }

            is CreateOrEditSaleSellerEvent.RemoveProduct -> blockingIntent {
                val lastState =
                    (state as? CreateOrEditSaleSellerState.CreateOrEdit) ?: return@blockingIntent
                val newProducts = lastState.products.toMutableMap()
                newProducts.remove(event.id)
                val newIsErrorAmountOfProduct = lastState.isErrorAmountOfProduct.toMutableMap()
                newIsErrorAmountOfProduct.remove(event.id)
                reduce {
                    lastState.copy(
                        products = newProducts,
                        isErrorAmountOfProduct = newIsErrorAmountOfProduct
                    )
                }
            }

            is CreateOrEditSaleSellerEvent.InputAmountOfProduct -> intent {
                val lastState =
                    (state as? CreateOrEditSaleSellerState.CreateOrEdit) ?: return@intent
                val amountNum = event.amount.toIntOrNull()
                val newProducts = lastState.products.toMutableMap()
                newProducts[event.id] = amountNum
                reduce {
                    lastState.copy(
                        products = newProducts,
                    )
                }
                calculationCheckPrice()
            }

            is CreateOrEditSaleSellerEvent.SelectCurrency -> intent {
                val lastState =
                    (state as? CreateOrEditSaleSellerState.CreateOrEdit) ?: return@intent
                reduce { lastState.copy(currency = event.currency) }
            }

            CreateOrEditSaleSellerEvent.Edit -> edit()
        }
    }

    override fun onError(error: Throwable) = intent {
        postSideEffect(CreateOrEditSaleSellerSideEffect.Message(R.string.network_problems))
        Log.e("CreateSaleSellerVM", error.message.toString())
    }

    private fun initState(
        createOrEditState: CreateOrEditState,
    ) = intent {
        when (createOrEditState) {
            CreateOrEditState.Create -> getMapIdToProductModelUseCase.execute()
                .onSuccess { map ->
                    reduce {
                        CreateOrEditSaleSellerState.CreateOrEdit(
                            createOrEditState = createOrEditState,
                            listProductForAdd = map,
                        )
                    }
                }
                .onFailure(::onError)

            CreateOrEditState.Edit -> {
                getSaleUseCase.execute(idSale!!)
                    .onSuccess { sale->
                        getMapIdToProductModelUseCase.execute()
                            .onSuccess { map->
                                reduce {
                                    CreateOrEditSaleSellerState.CreateOrEdit(
                                        createOrEditState = createOrEditState,
                                        listProductForAdd = map,
                                        listImageUri = sale.imagesUrl,
                                        name = sale.name,
                                        products = sale.products.associate { it.product.id to it.product.price.toInt() },
                                        checkPrice = sale.checkPrice,
                                        currency = sale.currency,
                                    )
                                }
                            }
                            .onFailure(::onError)
                    }
                    .onFailure(::onError)
            }
        }
    }

    private fun create() = intent {
        val lastState = (state as? CreateOrEditSaleSellerState.CreateOrEdit) ?: return@intent
        reduce {
            lastState.copy(isCreatingOrEditing = true)
        }
        val isErrorName = lastState.name.isBlank()
        val isErrorListImage = lastState.listImageUri.isEmpty()
        val isErrorAmountOfProduct = lastState.isErrorAmountOfProduct.toMutableMap()
        for ((id, amount) in lastState.products) {
            isErrorAmountOfProduct[id] = amount == null || amount == 0
        }
        val isErrorCommonAmountOfProduct =
            if (isErrorAmountOfProduct.values.isEmpty()) true
            else isErrorAmountOfProduct.values.max()
        if (
            isErrorName || isErrorListImage || isErrorCommonAmountOfProduct
        ) {
            reduce {
                lastState.copy(
                    isCreatingOrEditing = false,
                    isErrorName = isErrorName,
                    isErrorAmountOfProduct = isErrorAmountOfProduct
                )
            }
            if (isErrorListImage)
                postSideEffect(CreateOrEditSaleSellerSideEffect.Message(R.string.add_photos))
            if (isErrorAmountOfProduct.isEmpty())
                postSideEffect(CreateOrEditSaleSellerSideEffect.Message(R.string.add_products))
        } else {
            createSaleSellerUseCase.execute(
                createOrEditSaleModel = object : CreateOrEditSaleModel {
                    override val name = lastState.name
                    override val products = lastState.products.map {
                        object : AmountOfIdModel {
                            override val id = it.key
                            override val amount = it.value!!.toFloat()
                        }
                    }
                    override val checkPrice = lastState.checkPrice
                    override val currency = lastState.currency
                },
                listImageUri = lastState.listImageUri
            ).onSuccess {
                postSideEffect(CreateOrEditSaleSellerSideEffect.Created)
            }.onFailure {
                reduce {
                    lastState.copy(isCreatingOrEditing = false)
                }
            }
        }
    }

    private fun edit() = intent {
        val lastState = (state as? CreateOrEditSaleSellerState.CreateOrEdit) ?: return@intent
        reduce {
            lastState.copy(isCreatingOrEditing = true)
        }
        val isErrorName = lastState.name.isBlank()
        val isErrorListImage = lastState.listImageUri.isEmpty()
        val isErrorAmountOfProduct = lastState.isErrorAmountOfProduct.toMutableMap()
        for ((id, amount) in lastState.products) {
            isErrorAmountOfProduct[id] = amount == null || amount == 0
        }
        val isErrorCommonAmountOfProduct =
            if (isErrorAmountOfProduct.values.isEmpty()) true
            else isErrorAmountOfProduct.values.max()
        if (
            isErrorName || isErrorListImage || isErrorCommonAmountOfProduct
        ) {
            reduce {
                lastState.copy(
                    isCreatingOrEditing = false,
                    isErrorName = isErrorName,
                    isErrorAmountOfProduct = isErrorAmountOfProduct
                )
            }
            if (isErrorListImage)
                postSideEffect(CreateOrEditSaleSellerSideEffect.Message(R.string.add_photos))
            if (isErrorAmountOfProduct.isEmpty())
                postSideEffect(CreateOrEditSaleSellerSideEffect.Message(R.string.add_products))
        } else {
            editSaleSellerUseCase.execute(
                idSale = idSale!!,
                saleModel = object : CreateOrEditSaleModel {
                    override val name = lastState.name
                    override val products = lastState.products.map {
                        object : AmountOfIdModel {
                            override val id = it.key
                            override val amount = it.value!!.toFloat()
                        }
                    }
                    override val checkPrice = lastState.checkPrice
                    override val currency = lastState.currency
                },
                listImageUri = lastState.listImageUri
            ).onSuccess {
                postSideEffect(CreateOrEditSaleSellerSideEffect.Created)
            }.onFailure {
                reduce {
                    lastState.copy(isCreatingOrEditing = false)
                }
            }
        }
    }

    private fun calculationCheckPrice() = intent {
        val lastState = (state as? CreateOrEditSaleSellerState.CreateOrEdit) ?: return@intent
        var newCheckPrice = 0f
        for (item in lastState.products) {
            val amountInt = item.value ?: continue
            newCheckPrice += lastState.listProductForAdd.getValue(item.key).price * amountInt
        }
        reduce {
            lastState.copy(
                checkPrice = newCheckPrice
            )
        }
    }
}