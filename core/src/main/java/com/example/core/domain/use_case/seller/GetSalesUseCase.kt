package com.example.core.domain.use_case.seller

import com.example.core.domain.repos.SellerRepos

class GetSalesUseCase(
    private val sellerRepos: SellerRepos,
) {
    suspend fun execute() = runCatching {
        sellerRepos.getSales()
    }
}