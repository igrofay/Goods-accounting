package com.example.goodsaccounting.nav.model

internal sealed class StartingRouting(route: String) : AppRouting(route) {
    object Splash: StartingRouting("splash")
    object Auth : StartingRouting("auth")
    object RestorePassword : StartingRouting("restore_password")
    companion object{
        const val route = "starting"
    }
}