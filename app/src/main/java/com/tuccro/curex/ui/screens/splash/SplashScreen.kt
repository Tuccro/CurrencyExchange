package com.tuccro.curex.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
    onLoaded: () -> Unit
) {
    val viewModel: SplashViewModel = hiltViewModel()

    LaunchedEffect(viewModel) {
        viewModel.loaded = onLoaded
        viewModel.observeCurrencyRates()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "LOADING")
    }
}
