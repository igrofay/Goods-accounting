package com.example.goodsaccounting.nav.model

import com.example.goodsaccounting.R

internal sealed class SellerUserRouting (route:String) : AppRouting(route) {

    sealed class ComponentsWitBottomBar(route: String) : SellerUserRouting(route), BottomItemFeature{

        object Sales : ComponentsWitBottomBar("${route}_sales"){
            override val label = R.string.home
            override val icon = R.drawable.ic_home
        }

        object Analytics : ComponentsWitBottomBar("${route}_analytics") {
            override val label = R.string.statics
            override val icon = R.drawable.ic_bar_chart
        }
        object Profile : ComponentsWitBottomBar("${route}_profile") {
            override val label = R.string.profile
            override val icon = R.drawable.ic_person
        }
        companion object{
            const val route = "${SellerUserRouting.route}_components_wit_bottom_bar"
            val listBottomItemFeature = listOf<BottomItemFeature>(Sales, Analytics, Profile)
        }
    }
    object CreateSale: SellerUserRouting("${route}_create_sale")
    companion object{
        const val route = "seller_user"
    }
}