package com.example.goodsaccounting.nav.model

internal sealed class SellerUserRouting (route:String) : AppRouting(route) {
    companion object{
        const val route = "seller_user"
    }
}