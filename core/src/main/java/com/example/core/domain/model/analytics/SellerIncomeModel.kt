package com.example.core.domain.model.analytics

import com.example.core.domain.model.product.Currency

interface SellerIncomeModel {
    val currency: Currency
    val earningForToday: Float
    val soldToday: Float
    val earningThisMonth: List<IncomePerDateModel>
    val monthlyEarnings: List<IncomePerDateModel>
}