package com.example.core.domain.repos

import com.example.core.domain.model.warehouse.ReceiptOrWriteOffMaterialModel

interface WarehouseRepos {

    suspend fun getReceiptMaterial(): List<ReceiptOrWriteOffMaterialModel>

    suspend fun getWriteOffMaterial() : List<ReceiptOrWriteOffMaterialModel>


}