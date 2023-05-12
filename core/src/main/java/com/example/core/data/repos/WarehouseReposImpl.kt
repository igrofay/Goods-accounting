package com.example.core.data.repos

import com.example.core.data.data_soure.api.MaterialAccountingApi
import com.example.core.data.data_soure.api.SaleApi
import com.example.core.data.data_soure.api.WarehouseApi
import com.example.core.data.model.product.AmountOfMaterialBody
import com.example.core.data.model.sale.SaleBody
import com.example.core.data.model.warehouse.ReceiptOrWriteOffMaterialBody
import com.example.core.domain.model.product.AmountOfMaterialModel
import com.example.core.domain.model.sale.SaleModel
import com.example.core.domain.model.warehouse.ReceiptOrWriteOffMaterialModel
import com.example.core.domain.repos.WarehouseRepos
import io.ktor.client.call.body

internal class WarehouseReposImpl(
    private val materialAccountingApi : MaterialAccountingApi,
    private val saleApi : SaleApi,
    private val warehouseApi: WarehouseApi,
) : WarehouseRepos {
    override suspend fun getReceiptMaterial(): List<ReceiptOrWriteOffMaterialModel> {
        return materialAccountingApi.getReceiptMaterial().body<List<ReceiptOrWriteOffMaterialBody>>()
    }

    override suspend fun getWriteOffMaterial(): List<ReceiptOrWriteOffMaterialModel> {
        return materialAccountingApi.getWriteOffMaterial().body<List<ReceiptOrWriteOffMaterialBody>>()
    }

    override suspend fun getAllSale(): List<SaleModel> {
        return saleApi.getAllSale().body<List<SaleBody>>()
    }

    override suspend fun getListAmountOfMaterial(): List<AmountOfMaterialModel> =
        warehouseApi.getListAmountOfMaterial().body<List<AmountOfMaterialBody>>()

}