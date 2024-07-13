package com.vico.titungan.ui.screen.game

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vico.titungan.R
import com.vico.titungan.model.Player
import com.vico.titungan.model.TitunganCell
import com.vico.titungan.ui.component.text.AnimatingText
import com.vico.titungan.ui.component.RingShape
import com.vico.titungan.ui.theme.BluePastel
import com.vico.titungan.ui.theme.TitunganTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun GameScreenPreview() {
    val navController: NavHostController = rememberNavController()

    TitunganTheme() {
        GameScreen("Vico", "Ridho", 5, 15, 3, 1, 0, 0, arrayOf("+", "-"), 1)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    player1 : String,
    player2 : String,
    nyawa : Int,
    waktu : Int,
    tiles : Int,
    caraMenang : Int,
    defisitSkor : Int,
    maksimumSkor : Int,
    listOperators : Array<String>,
    playOrder : Int
) {

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val gameState = rememberHomeState()

    LaunchedEffect(Unit) {
        gameState.newGame(
            player1,
            player2,
            nyawa,
            tiles,
            listOperators,
            playOrder
        )
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                            val isRunning = true
                            var timeLeft by remember { mutableIntStateOf(waktu) }
                            var dropDownMenuExpanded by remember { mutableStateOf(false) }

                            LaunchedEffect(isRunning) {
                                while (isRunning) {
                                    for (second in waktu downTo 0) {
                                        timeLeft = second

                                        if(gameState.isPlayerInputRightValue.value) {
                                            gameState.isPlayerInputRightValue.value = false
                                            timeLeft = waktu
                                            break
                                        }

                                        delay(1000L)
                                    }

                                    //waktu dio abes
                                    if (timeLeft == 0) {
                                        gameState.currentPlayer.value?.life = gameState.currentPlayer.value?.life!! - 1
                                        gameState.changePlayer()
                                        timeLeft = waktu
                                    }
                                }
                            }

                            CountDownTimer(timeLeft = timeLeft)

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

                            AnimatedVisibility(
                                visible = gameState.isGameStarted.value,
                                enter = scaleIn(),
                                exit = scaleOut(),
                                content = {

                                    FormInputNumber(
                                        dropDownMenuExpanded = dropDownMenuExpanded,
                                        numberInputEnabled = gameState.activeCell.value != null,
                                        numberInput1 = gameState.numberInput1.value,
                                        numberInput1OnValueChange = { gameState.numberInput1.value = it },
                                        numberInput2 = gameState.numberInput2.value,
                                        numberInput2OnValueChange = { gameState.numberInput2.value = it },
                                        selectedOperator = gameState.selectedOperator.value,
                                        dropDownMenuOnClick = { dropDownMenuExpanded = true },
                                        dropDownMenuOnDismissRequest = { dropDownMenuExpanded = false },
                                        dropDownMenuItemOnClick = {
                                            gameState.selectedOperator.value = it
                                            dropDownMenuExpanded = false
                                        },
                                        buttonEnabled = gameState.numberInput1.value.isNotEmpty() && gameState.numberInput2.value.isNotEmpty(),
                                        buttonOnClick = {
                                            gameState.checkIsRightAnswer(caraMenang,defisitSkor, maksimumSkor)
                                            gameState.isPlayerInputRightValue.value = true

                                            if (gameState.showSnackbar.value) {
                                                scope.launch {
                                                    snackbarHostState.showSnackbar(
                                                        message = "Hasil anda salah"
                                                    )
                                                    gameState.showSnackbar.value = false
                                                }
                                            }
                                        },
                                        buttonBorderColor = if (gameState.numberInput1.value.isNotEmpty() && gameState.numberInput2.value.isNotEmpty()) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background,
                                        listOperators = listOperators
                                    )

                                }
                            )

                        }
                    )
                }
            )
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormInputNumber(
    dropDownMenuExpanded : Boolean,
    numberInputEnabled : Boolean,
    numberInput1 : String,
    numberInput1OnValueChange : (String) -> Unit,
    numberInput2 : String,
    numberInput2OnValueChange : (String) -> Unit,
    selectedOperator : String,
    dropDownMenuOnClick : () -> Unit,
    dropDownMenuOnDismissRequest : () -> Unit,
    dropDownMenuItemOnClick : (String) -> Unit,
    buttonEnabled : Boolean,
    buttonOnClick : () -> Unit,
    buttonBorderColor : Color,
    listOperators: Array<String>
) {

    Column (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            OutlinedTextField(
                enabled = numberInputEnabled,
                value = numberInput1,
                onValueChange = { numberInput1OnValueChange(it) },
                placeholder = {
                    Text(stringResource(id = R.string.input_number_1_name),
                        color = Color.Gray)
                },
                modifier = Modifier
                    .weight(2f)
                    .height(50.dp)
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(100),
                    ),
                textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.SemiBold),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .clickable { dropDownMenuOnClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    selectedOperator,
                    modifier = Modifier.padding(4.dp),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 26.sp
                    )
                )
                DropdownMenu(
                    expanded = dropDownMenuExpanded,
                    onDismissRequest = dropDownMenuOnDismissRequest
                ) {
                    listOperators.forEach { operator ->
                        DropdownMenuItem(onClick = { dropDownMenuItemOnClick(operator) }, text = { Text(
                            operator,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 16.sp
                            )
                        )})
                    }
                }
            }

            OutlinedTextField(
                enabled = numberInputEnabled,
                value = numberInput2,
                onValueChange = numberInput2OnValueChange,
                placeholder = {
                    Text(stringResource(id = R.string.input_number_2_name),
                        color = Color.Gray)
                },
                modifier = Modifier
                    .weight(2f)
                    .height(50.dp)
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(100),
                    ),
                textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.SemiBold),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            Button(
                enabled = buttonEnabled,
                onClick = buttonOnClick,
                border = BorderStroke(
                    width = 3.dp,
                    color = buttonBorderColor,
                ),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(2f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.DarkGray.copy(alpha = 0.7f),
                ),
                shape = CircleShape
            ) {
                Text(
                    text = "Cek",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.background,
                    )
                )
            }
        }
    }
}


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
    val itemMargin = 4.dp
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
                        targetValue =
                            if (cell == activeCell)
                                MaterialTheme.colorScheme.secondary
                            else if (cell.owner != null)
                                Color.Transparent
                            else
                                MaterialTheme.colorScheme.primary,
                        animationSpec = tween(durationMillis = 300),
                        label = "backgroundColorAnimated"
                    )

                    TitunganItem(
                        cell,
                        clickable = !isGameFinished && cell.owner == null,
                        shape = shapeProvider(cell.owner),
                        size = boxItemSize,
                        hasOwner = cell.owner != null,
                        onClick = { onItemClick(cell) },
                        backgroundColor = backgroundColorAnimated,
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
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .border(
                width = if (hasOwner) 2.dp else 4.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (hasOwner) 0.5f else 1f),
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = BluePastel),
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
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold
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
                                .background(
                                    if (shape == RingShape) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary
                                ),
                        )
                    }
                }
            )
        }
    )
}

@Composable
internal fun CountDownTimer(
    modifier: Modifier = Modifier,
    timeLeft : Int
) {
    Row(
        modifier = modifier
            .width(60.dp)
            .clip(RoundedCornerShape(100))
            .background(color = MaterialTheme.colorScheme.background)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(100),
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        AnimatingText(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f),
            time = timeLeft.toString()
        )
    }
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
