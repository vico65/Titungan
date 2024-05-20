package com.vico.titungan.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vico.titungan.R
import com.vico.titungan.domain.navigation.Screen
import com.vico.titungan.ui.component.AppButton
import com.vico.titungan.ui.component.displayLarge
import com.vico.titungan.ui.theme.TitunganTheme
import com.vico.titungan.ui.theme.border2dp
import com.vico.titungan.ui.theme.padding16dp
import com.vico.titungan.ui.theme.width248dp
import com.vico.titungan.ui.theme.padding64dp

@Preview(showBackground = true, device = Devices.DEFAULT, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HelloJetpackComposeAppPreview() {
    var navController: NavHostController = rememberNavController()

    TitunganTheme() {
        MenuScreen(navController)
    }
}

@Composable
fun MenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding16dp)
            .border(width = border2dp, color = MaterialTheme.colorScheme.onBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current
        displayLarge(text = stringResource(id = R.string.app_name))
        AppButton(
            modifier = Modifier
                .width(width248dp)
                .padding(top = padding64dp),
            text = stringResource(R.string.new_game)
        ) {  } //context.launchActivity<GameActivity>()
        AppButton(
            modifier = Modifier.width(width248dp),
            text = stringResource(id = R.string.high_score)
        ) {
            navController.navigate(Screen.HighScores.route)
        }
        AppButton(modifier = Modifier.width(width248dp), text = stringResource(R.string.settings)) {
            navController.navigate(Screen.Settings.route)
        }
        AppButton(modifier = Modifier.width(width248dp), text = stringResource(R.string.about)) {
            navController.navigate(Screen.About.route)
        }
    }
}