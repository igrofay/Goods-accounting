package com.example.goodsaccounting.nav.model

import com.example.goodsaccounting.R

internal sealed class NoneUserRouting(route: String) : AppRouting(route){
    object ListProduct : NoneUserRouting("${route}_list_product"), BottomItemFeature{
        override val label = R.string.home
        override val icon = R.drawable.ic_home
    }
    object Profile : NoneUserRouting("${route}_profile"), BottomItemFeature {
        override val label = R.string.profile
        override val icon = R.drawable.ic_person
    }

    companion object{
        const val route = "none_user"
        val listBottomItemFeature = listOf<BottomItemFeature>(ListProduct, Profile)
    }
}