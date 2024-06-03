package com.vico.titungan.ui.screen.game

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.twotone.Build
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import com.vico.titungan.ui.component.ScoreBoard
import com.vico.titungan.ui.theme.TitunganTheme
import com.vico.titungan.ui.theme.fontFamilyFredoka
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


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

    gameState.newGame()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
                            var totalTime = 30
                            var isRunning = true
                            var timeLeft by remember { mutableIntStateOf(totalTime) }

                            LaunchedEffect(isRunning) {
                                while (isRunning) {
                                    for (second in totalTime downTo 0) {
                                        timeLeft = second

                                        if(gameState.isPlayerInputRightValue.value) {
                                            gameState.isPlayerInputRightValue.value = false
                                            timeLeft = totalTime
                                            break
                                        }

                                        delay(1000L)
                                    }

                                    //waktu dio abes
                                    if (timeLeft == 0) {
                                        gameState.changePlayer()
                                        timeLeft = totalTime
                                    }
                                }
                            }

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(color = Color(0xFF01579B), shape = CircleShape)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "$timeLeft",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 24.sp
                                    )
                                )
                            }

                            AnimatedVisibility(
                                visible = true,
                                content = {
                                    ScoreBoard(
                                        players = gameState.players.value,
                                        currentPlayer = gameState.currentPlayer.value
                                    )
                                }
                            )

                            AnimatedVisibility(
                                visible = gameState.isGameStarted.value,
                                enter = scaleIn(),
                                exit = scaleOut(),
                                content = {
                                    GameBoard(
                                        gameSize = gameState.gameSize.intValue,
                                        gameCells = gameState.gameCells.value,
                                        isGameFinished = gameState.isGameFinished.value,
                                        activeCell = gameState.activeCell.value,
                                        shapeProvider = gameState::getOwnerShape,
                                        onItemClick = gameState::changeActiveCell
                                    )
                                }
                            )
//                            Spacer(modifier = Modifier.height(64.dp))
                            AnimatedVisibility(
                                visible = gameState.isGameStarted.value,
                                enter = scaleIn(),
                                exit = scaleOut(),
                                content = {
                                    var expanded by remember { mutableStateOf(false) }
                                    val operators = listOf("+", "-", "x", "/")

                                    Column (
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            // Input angka pertama
                                            OutlinedTextField(
                                                value = gameState.numberInput1.value,
                                                onValueChange = { gameState.numberInput1.value = it },
                                                label = { Text("Angka 1", color = Color.Gray) },
                                                modifier = Modifier
                                                    .weight(2f)
                                                    .height(56.dp), // Tinggi yang lebih besar
                                                textStyle = TextStyle(color = Color.Black),
                                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                                    focusedBorderColor = Color(0xFF01579B), // Warna biru yang cerah saat fokus
                                                    unfocusedBorderColor = Color(0xFF01579B).copy(alpha = 0.3f), // Warna biru yang cerah saat tidak fokus
                                                    cursorColor = Color(0xFF01579B) // Warna kursor biru yang cerah
                                                )// Warna teks hitam
                                            )

                                            // Dropdown menu untuk operator
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clickable { expanded = true },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    gameState.selectedOperator.value,
                                                    modifier = Modifier.padding(10.dp),
                                                    style = TextStyle(
                                                        color = Color(0xFF01579B), // Warna biru yang cerah
                                                        fontSize = 24.sp
                                                    )
                                                )
                                                DropdownMenu(
                                                    expanded = expanded,
                                                    onDismissRequest = { expanded = false }
                                                ) {
                                                    operators.forEach { operator ->
                                                        DropdownMenuItem(onClick = {
                                                            gameState.selectedOperator.value = operator
                                                            expanded = false
                                                        }, text = { Text(
                                                            operator,
                                                            style = TextStyle(
                                                                color = Color(0xFF01579B), // Warna biru yang cerah
                                                                fontSize = 16.sp
                                                            )
                                                        )})
                                                    }
                                                }
                                            }

                                            // Input angka kedua
                                            OutlinedTextField(
                                                value = gameState.numberInput2.value,
                                                onValueChange = { gameState.numberInput2.value = it },
                                                label = { Text("Angka 2", color = Color.Gray) },
                                                modifier = Modifier
                                                    .weight(2f)
                                                    .height(56.dp), // Tinggi yang lebih besar
                                                textStyle = TextStyle(color = Color.Black),
                                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                                    focusedBorderColor = Color(0xFF01579B),
                                                    unfocusedBorderColor = Color(0xFF01579B).copy(alpha = 0.3f),
                                                    cursorColor = Color(0xFF01579B)
                                                )
                                            )

                                            //ini adalah button hasil
                                            Button(
                                                onClick = {
                                                    Log.i("apakah player input right value", gameState.isPlayerInputRightValue.value.toString())
                                                          gameState.checkWinStatus()
                                                },
                                                modifier = Modifier // Besar ikon
                                                    .padding(start = 16.dp)
                                                    .weight(2f), // Spasi di sekitar tombol
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFF01579B),
                                                    contentColor = Color.White
                                                ),
                                                shape = CircleShape // Bentuk tombol bulat
                                            ) {
                                                Icon(Icons.Default.PlayArrow, contentDescription = "Hitung")
                                            }
                                        }
                                    }


                                }
                            )

                        }
                    )
                }
            )
        }
    )

}

//@Composable
//private fun formInputNumber(
//    gameCells: List<List<TitunganCell>>,
//    isGameFinished: Boolean,
//    numberInput1: Int,
//    numberInput2: Int,
//    onItemClick: (TitunganCell) -> Unit
//) {
//
//}


@Composable
private fun GameBoard(
    gameSize: Int,
    gameCells: List<List<TitunganCell>>,
    isGameFinished: Boolean,
    activeCell: TitunganCell?,
    shapeProvider: (Player?) -> Shape,
    onItemClick: (TitunganCell) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val boxPadding = 16.dp
    val boxSize = screenWidth - (2 * boxPadding.value).dp
    val itemMargin = 6.dp
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

                    val backgroundColorAnimated by animateColorAsState(
                        targetValue = if (cell == activeCell) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                        animationSpec = tween(durationMillis = 300),
                        label = "backgroundColorAnimated"
                    )

                    Log.d("Cell Owner", "${cell.owner != null}")

                    TitunganItem(
                        cell,
                        clickable = !isGameFinished && cell.owner == null,
                        shape = shapeProvider(cell.owner),
                        size = boxItemSize,
                        hasOwner = cell.owner != null,
                        onClick = {
                            Log.d("GameBoard", "Cell clicked at (${cell.x}, ${cell.y} and has active = ${cell.isActive}")


                            onItemClick(cell)
                                  },
                        backgroundColor = backgroundColorAnimated,
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
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color(0xFF81D4FA)),
                enabled = clickable,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
        content = {
            Log.i("Status compose", "has owner = $hasOwner")

            AnimatedVisibility(

                visible = !hasOwner,
                content = {
                    if (!hasOwner) {
                        Text(
                            text = cell.number.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.displayLarge.copy(fontFamily = fontFamilyFredoka),
                            fontSize = 28.sp,
                        )
                    }
                }
            )

            AnimatedVisibility(
                visible = hasOwner,
                enter = scaleIn(animationSpec = tween(150)),
                exit = scaleOut(animationSpec = tween(150)) + fadeOut(),
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
