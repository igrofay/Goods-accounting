package com.example.core.data.repos

import com.example.core.domain.model.warehouse.ReceiptOrWriteOffMaterialModel
import com.example.core.domain.repos.WarehouseRepos

internal class WarehouseReposImpl : WarehouseRepos {
    override suspend fun getReceiptMaterial(): List<ReceiptOrWriteOffMaterialModel> {
        return listOf()
    }

    override suspend fun getWriteOffMaterial(): List<ReceiptOrWriteOffMaterialModel> {
        return listOf()
    }
}