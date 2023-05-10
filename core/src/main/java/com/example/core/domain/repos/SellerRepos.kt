package com.example.core.domain.repos

import com.example.core.domain.model.create.CreateOrEditSaleModel
import com.example.core.domain.model.create.IdModel
import com.example.core.domain.model.sale.SaleModel

interface SellerRepos {
    suspend fun getSales() : List<SaleModel>
    suspend fun getSale(idSale: String) : SaleModel
}