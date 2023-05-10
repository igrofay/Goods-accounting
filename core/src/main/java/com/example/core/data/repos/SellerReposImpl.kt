package com.example.core.data.repos

import com.example.core.data.data_soure.api.SaleApi
import com.example.core.data.model.sale.SaleBody
import com.example.core.domain.model.sale.SaleModel
import com.example.core.domain.repos.SellerRepos
import io.ktor.client.call.body

internal class SellerReposImpl(
    private val sellerApi: SaleApi,
) : SellerRepos {
    override suspend fun getSales(): List<SaleModel> =
        sellerApi.getSellerSales().body<List<SaleBody>>()
    override suspend fun getSale(idSale: String) : SaleModel =
        sellerApi.getSale(idSale).body<SaleBody>()
}