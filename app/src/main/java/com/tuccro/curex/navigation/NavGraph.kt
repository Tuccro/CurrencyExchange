package com.tuccro.curex.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tuccro.curex.ui.screens.WalletScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    navController.addOnDestinationChangedListener(listener = { controller, destination, arguments ->
        Log.e("NavGraph", "OnDestinationChangedListener destination=$destination")
    })

    NavHost(navController = navController, startDestination = "wallet") {
        composable("wallet") {
            WalletScreen()
        }
    }
}