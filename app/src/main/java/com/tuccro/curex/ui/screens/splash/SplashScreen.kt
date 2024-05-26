package com.tuccro.curex.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tuccro.curex.R

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
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.splash),
                contentDescription = "I hope it looks like a wallet",
                Modifier.size(200.dp)
            )
            Text(
                text = stringResource(R.string.loading),
                style = MaterialTheme.typography.titleLarge
            )
            CircularProgressIndicator()
        }
    }
}
