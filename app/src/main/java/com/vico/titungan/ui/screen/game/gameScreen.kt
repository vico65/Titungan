package com.vico.titungan.ui.screen.game

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.MaterialTheme.colors
import com.vico.titungan.model.Player
import com.vico.titungan.model.TitunganCell
import com.vico.titungan.ui.component.PlayerCards
import com.vico.titungan.ui.theme.TitunganTheme


@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    var navController: NavHostController = rememberNavController()

    TitunganTheme() {
        GameScreen(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    navController: NavController,
//    viewModel: GameViewModel
) {

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val gameState = rememberHomeState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    gameState.newGame()

    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { contentPadding ->
            Surface (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(horizontal = 16.dp),
                content = {
                    Column (
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .verticalScroll(rememberScrollState()),
                        content = {
//                            AnimatedVisibility(
////                                visible = gameState.isGameStarted.value,
//                                visible = true,
//                                enter = slideInHorizontally(
//                                    initialOffsetX = { it },
//                                    animationSpec = tween(300)
//                                ),
//                                content = {
//                                    PlayerCards(
//                                        players = gameState.players.value,
//                                        currentPlayer = gameState.currentPlayer.value
//                                    )
////                                    var player1 = Player("First Player", PointType.O)
////                                    var player2 = Player("Second Player", PointType.X)
////
////                                    var players = buildList {
////                                        add(player1)
////                                        add(player2)
////                                    }
////
////                                    PlayerCards(
////                                        players = players,
////                                        currentPlayer = player1
////                                    )
//                                }
//                            )

                            AnimatedVisibility(
//                                visible = gameState.isGameStarted.value && !gameState.isRollingDices.value,
                                visible = true,
                                enter = scaleIn(),
                                exit = scaleOut(),
                                content = {
                                    GameBoard(
                                        gameSize = gameState.gameSize.intValue,
                                        gameCells = gameState.gameCells.value,
                                        isGameFinished = gameState.isGameFinished.value,
                                        shapeProvider = gameState::getOwnerShape,
                                        onItemClick = gameState::playCell
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(64.dp))
                        }
                    )
                }
            )
        }
    )

}

@Composable
private fun GameBoard(
    gameSize: Int,
    gameCells: List<List<TitunganCell>>,
    isGameFinished: Boolean,
    shapeProvider: (Player?) -> Shape,
    onItemClick: (TitunganCell) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val boxPadding = 16.dp
    val boxSize = screenWidth - (2 * boxPadding.value).dp
    val itemMargin = 8.dp
    val boxItemSize = ((boxSize.value - ((itemMargin.value * (gameSize - 1)))) / gameSize).dp

    LazyVerticalGrid(
        modifier = Modifier.size(boxSize),
        columns = GridCells.Fixed(gameSize),
        horizontalArrangement = Arrangement.spacedBy(itemMargin),
        verticalArrangement = Arrangement.spacedBy(itemMargin),
        userScrollEnabled = false,
        content = {
            gameCells.forEachIndexed { _, row ->
                itemsIndexed(row) { _, cell ->
//                    val colors = if (cell in winnerCells)
//                        MaterialTheme.colorScheme.secondary to MaterialTheme.colorScheme.onSecondary
//                    else MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
                    TitunganItem(
                        cell,
                        clickable = !isGameFinished && cell.owner == null,
                        shape = shapeProvider(cell.owner),
                        size = boxItemSize,
                        hasOwner = cell.owner != null,
                        onClick = { onItemClick(cell) },
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}

@Composable
private fun TitunganItem(
    cell: TitunganCell,
    shape: Shape,
    clickable: Boolean,
    size: Dp,
    hasOwner: Boolean,
    backgroundColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                enabled = clickable,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
        content = {
            AnimatedVisibility(
                visible = !hasOwner,
                content = {
                    if (!hasOwner) {
                        Text(
                            text = cell.number.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.displayLarge,
                            fontSize = 24.sp
                        )
                    }
                }
            )

            AnimatedVisibility(
                visible = hasOwner,
                enter = scaleIn(animationSpec = tween(150)),
                content = {
                    if (hasOwner) {
                        Box(
                            modifier = Modifier
                                .size((size.value / 2).dp)
                                .clip(shape)
                                .background(contentColor),
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
