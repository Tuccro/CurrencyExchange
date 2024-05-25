package com.tuccro.curex.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tuccro.curex.navigation.Routes.SCREEN_SPLASH
import com.tuccro.curex.navigation.Routes.SCREEN_WALLET
import com.tuccro.curex.ui.screens.splash.SplashScreen
import com.tuccro.curex.ui.screens.wallet.WalletScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    navController.addOnDestinationChangedListener(listener = { controller, destination, arguments ->
        Log.e("NavGraph", "OnDestinationChangedListener destination=$destination")
    })

    NavHost(navController = navController, startDestination = SCREEN_SPLASH) {
        composable(SCREEN_SPLASH) {
            SplashScreen(onLoaded = {
                navController.navigate(SCREEN_WALLET) {
                    popUpTo(SCREEN_SPLASH) { inclusive = true }
                }
            })
        }
        composable(SCREEN_WALLET) {
            WalletScreen()
        }
    }
}

object Routes {
    const val SCREEN_SPLASH = "splash"
    const val SCREEN_WALLET = "wallet"
}
