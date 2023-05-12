package com.example.core.domain.repos

import com.example.core.domain.model.product.AmountOfMaterialModel
import com.example.core.domain.model.sale.SaleModel
import com.example.core.domain.model.warehouse.ReceiptOrWriteOffMaterialModel

interface WarehouseRepos {

    suspend fun getReceiptMaterial(): List<ReceiptOrWriteOffMaterialModel>

    suspend fun getWriteOffMaterial() : List<ReceiptOrWriteOffMaterialModel>

    suspend fun getAllSale() : List<SaleModel>

    suspend fun getListAmountOfMaterial() : List<AmountOfMaterialModel>
}