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

    private fun test1() = intent {
        reduce {
            state.copy(
                listProductModel = listOf(
                    object : ProductModel {
                        override val id = UUID.randomUUID().toString()
                        override val imageUrl =
                            "https://epnew.ru/wp-content/uploads/2019/11/magnit-s-foto-10h10-sm-500x500.jpg"
                        override val name = "Магнит с фото (маленький)"
                        override val price = 199f
                        override val currency = Currency.Rub
                        override val materials = listOf(
                                object : AmountOfMaterialModel {
                                    override val amount = 2f
                                    override val materialModel = object : MaterialModel {
                                        override val id = UUID.randomUUID().toString()
                                        override val name = "Магнит 25x25"
                                        override val measurement = Measurements.Piece
                                        override val imageUrl = null
                                    }
                                },
                                object : AmountOfMaterialModel {
                                    override val amount = 1f
                                    override val materialModel = object : MaterialModel{
                                        override val id = UUID.randomUUID().toString()
                                        override val name = "Рамка 120x72"
                                        override val measurement = Measurements.Piece
                                        override val imageUrl = null
                                    }

                                },
                            )
                    },
                    object : ProductModel {
                        override val id = UUID.randomUUID().toString()
                        override val imageUrl = null

                        //                            "https://39magnets.ru/wp-content/uploads/2019/08/akrilovyj-magnit-55-80.png"
                        override val name = "Магнит с фото"
                        override val price = 399f
                        override val currency = Currency.Rub
                        override val materials: List<AmountOfMaterialModel>
                            get() = listOf()
                    },
                )
            )
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
