package com.vico.titungan

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vico.titungan.ui.navigation.Nav
import com.vico.titungan.ui.screen.CustomGameScreen
import com.vico.titungan.ui.screen.MenuScreen
import com.vico.titungan.ui.screen.game.GameConstants
import com.vico.titungan.ui.screen.game.GameScreen
import com.vico.titungan.ui.theme.TitunganTheme
import com.vico.titungan.util.Constants.theme

@Composable
internal fun MainNavigation() {
    val context = LocalContext.current
//    var theme by remember { mutableStateOf(ThemeSetting.System) }
//    val dataStore = DataStoreHelper(context.settings)

//    LaunchedEffect(Unit) {
//        theme = ThemeSetting.valueOf(
//            dataStore.getString(Constants.theme) ?: ThemeSetting.System.name
//        )
//    }
    TitunganTheme(
//        isDarkTheme = isDarkTheme(theme, isSystemInDarkTheme()),
        content = {
            Column {
                val navController = rememberNavController()
                NavHost(
                    modifier = Modifier.weight(1f),
                    navController = navController,
                    startDestination = Nav.Routes.mainMenu,
                    builder = {
                        composable(Nav.Routes.mainMenu) {
                            MenuScreen(onNavigateToGame = {navController.navigate(Nav.Routes.game)})
                        }
                        composable(
                            Nav.Routes.game,
                            arguments = listOf(
                                navArgument("player1") {type = NavType.StringType},
                                navArgument("player2") {type = NavType.StringType},
                                navArgument("nyawa") {type = NavType.IntType},
                                navArgument("waktu") {type = NavType.IntType},
                                navArgument("tiles") {type = NavType.IntType},
                                navArgument("caraMenang") {type = NavType.IntType},
                                navArgument("defisitSkor") {type = NavType.IntType},
                                navArgument("maksimumSkor") {type = NavType.IntType},
                                navArgument("listOperators") {type = NavType.StringArrayType},
                                navArgument("playOrder") {type = NavType.IntType},
                            )
                        ) {
                            val player1 = it.arguments?.getString("player1")?: R.string.first_player_default_name
                            val player2 = it.arguments?.getString("player2")?: R.string.second_player_default_name
                            val nyawa = it.arguments?.getInt("nyawa")?: 3
                            val waktu = it.arguments?.getInt("waktu")?: 30
                            val tiles = it.arguments?.getInt("tiles")?: 5
                            val caraMenang = it.arguments?.getInt("caraMenang")?: 1
                            val defisitSkor = it.arguments?.getInt("defisitSkor")?: 0
                            val maksimumSkor = it.arguments?.getInt("maksimumSkor")?: 0
                            val listOperators = it.arguments?.getStringArray("listOperators")?: GameConstants.listOperators.toTypedArray()
                            val playOrder = it.arguments?.getInt("playOrder")?: 1
                            GameScreen(
                                player1 = player1.toString(),
                                player2 = player2.toString(),
                                nyawa = nyawa,
                                waktu = waktu,
                                tiles = tiles,
                                caraMenang = caraMenang,
                                defisitSkor = defisitSkor,
                                maksimumSkor = maksimumSkor,
                                listOperators = listOperators,
                                playOrder = playOrder
                            )
                        }
                        composable(Nav.Routes.customGame) {
                            CustomGameScreen(navController)
                        }

//                        composable(Nav.Routes.settings) {
//                            SettingsContent(
//                                onThemeChanged = { newTheme -> theme = newTheme },
//                                onBackClick = { navController.popBackStack() }
//                            )
//                        }
//
//                        composable(Nav.Routes.about) { AboutContent(onBackClick = { navController.popBackStack() }) }
                    }
                )
            }
        }
    )
}

//private fun isDarkTheme(
//    themeSetting: ThemeSetting,
//    isSystemInDarkTheme: Boolean
//) = when (themeSetting) {
//    ThemeSetting.Light -> false
//    ThemeSetting.System -> isSystemInDarkTheme
//    else -> themeSetting == ThemeSetting.Dark
//}