package com.vico.titungan.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import com.vico.titungan.ui.navigation.Nav
import com.vico.titungan.R

@Composable
fun SplashScreen(navController: NavHostController) {
    var alpha by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        alpha = 1f
        delay(3000) // Durasi splash screen
        navController.navigate(Nav.Routes.mainMenu) {
            popUpTo(Nav.Routes.splashScreen) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .alpha(alpha),
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "By Alvico",
                color = MaterialTheme.colorScheme.background,
                fontSize = 20.sp,
                modifier = Modifier.alpha(alpha)
            )
        }
    }
}