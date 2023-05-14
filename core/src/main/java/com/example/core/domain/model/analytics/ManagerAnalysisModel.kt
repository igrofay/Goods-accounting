package com.example.core.domain.model.analytics

import com.example.core.domain.model.product.AmountOfProductModel
import com.example.core.domain.model.product.Currency

interface ManagerAnalysisModel {
    val currency: Currency

    val soldToday: Float
    val salesStatisticsForToday : List<SellerIncomeModel>
    val salesStatisticsThisMonth: List<SellerIncomeModel>
    // Продаваемый продукт за сегодня
    val sellingProductToday : AmountOfProductModel

    val productSalesForToday : List<AmountOfProductModel>
    val productSalesThisMonth : List<AmountOfProductModel>
}