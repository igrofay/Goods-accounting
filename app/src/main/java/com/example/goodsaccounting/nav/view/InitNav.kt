package com.example.goodsaccounting.nav.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.nav.model.AdministratorUserRouting
import com.example.goodsaccounting.nav.model.ManagerUserRouting
import com.example.goodsaccounting.nav.model.NoneUserRouting
import com.example.goodsaccounting.nav.model.SellerUserRouting
import com.example.goodsaccounting.nav.model.StartingRouting

@Composable
internal fun InitNav() {
    val appNavController = rememberNavController()
    CompositionLocalProvider(
        LocalAppNavController provides appNavController
    ) {
        NavHost(navController = appNavController, startDestination = StartingRouting.route) {
            staringNav()
            noneUserNav()
            sellerUserNav()
            administratorUserNav()
            managerUserNav()
        }
    }
}

val LocalAppNavController = staticCompositionLocalOf<NavHostController> { error("Not Found AppNavController") }

internal fun NavController.navWithClearStack(route:String){
    val lastRoute = currentBackStackEntry?.destination?.route ?: return
    navigate(route){
        popUpTo(lastRoute){
            inclusive = true
        }
    }
}
internal fun NavController.navigateToUserRole(role: RoleLevel){
    val lastRoute = currentBackStackEntry?.destination?.route ?: return
    when(role){
        RoleLevel.None -> navigate(NoneUserRouting.route){
            popUpTo(lastRoute){ inclusive = true}
        }
        RoleLevel.Seller -> navigate(SellerUserRouting.route){
            popUpTo(lastRoute){ inclusive = true}
        }
        RoleLevel.Manager -> navigate(ManagerUserRouting.route){
            popUpTo(lastRoute){ inclusive = true}
        }
        RoleLevel.Administrator -> navigate(AdministratorUserRouting.route){
            popUpTo(lastRoute){ inclusive = true}
        }
    }
}
