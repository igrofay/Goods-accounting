package com.example.core.domain.use_case.seller

import com.example.core.domain.repos.SellerRepos

class GetSaleUseCase(
    private val sellerRepos: SellerRepos,
){
    suspend fun execute(idSale: String) = runCatching {
        sellerRepos.getSale(idSale)
    }
}