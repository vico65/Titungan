package com.vico.titungan.ui.screen.game

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import com.vico.titungan.R
import com.vico.titungan.logic.SimpleGameLogic
import com.vico.titungan.model.Operator
import com.vico.titungan.model.Player
import com.vico.titungan.model.TitunganCell
import com.vico.titungan.ui.component.RingShape
import com.vico.titungan.ui.component.XShape
import com.vico.titungan.ui.component.toName
import com.vico.titungan.ui.component.toShape
import com.vico.titungan.ui.screen.game.GameConstants.gameDefaultSize
import io.github.yamin8000.dooz.game.logic.GameLogic
import kotlin.random.Random

class GameState (
    private val hapticFeedback: HapticFeedback,
    private val context: Context,

    var gameCells: MutableState<List<List<TitunganCell>>>,
    var gameSize: MutableIntState,
    var currentPlayer : MutableState<Player?>,
    var players: MutableState<List<Player>>,
    var isGameStarted: MutableState<Boolean>,
    var isGameFinished: MutableState<Boolean>,
    var winner: MutableState<Player?>,

//    var isGameDrew: MutableState<Boolean>
//    val isRollingDices: MutableState<Boolean>,

    //buat koncang dadu penentu siapo yg pertamo
//    var firstPlayerPolicy: MutableState<FirstPlayerPolicy>,

    var numberInput1: MutableState<String>,
    var numberInput2: MutableState<String>,
    var selectedOperator: MutableState<String>

    ) {

    private var isSoundOn = true
    private var isVibrationOn = true
    private var gameLogic: GameLogic? = null
//    private val dataStore = DataStoreHelper(context.settings)

    init {
        isSoundOn = true
        isVibrationOn = true
        prepareGame()
    }

    fun newGame() {
//        scope.launch {
//            if (firstPlayerPolicy.value == FirstPlayerPolicy.DiceRolling)
//                dummyDiceRolling()
//        }

        prepareGame()
        isGameStarted.value = true
    }
    private fun prepareGame() {
        resetGame()
        prepareGameRules()
        preparePlayers()
        prepareGameLogic()
    }

    private fun resetGame() {
        winner.value = null
        isGameFinished.value = false
        isGameStarted.value = false
//        isGameDrew.value = false
        gameCells.value = getEmptyBoard()
    }

    fun playCell(
        cell: TitunganCell
    ) {
        Log.d("GameState", "Playing cell at (${cell.x}, ${cell.y})")
        checkIfGameIsFinished()
        changeCellOwner(cell)
        checkIfGameIsFinished()
    }

    fun changeActiveCell(
        cell: TitunganCell
    ) {
        if (isVibrationOn)
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        if (isSoundOn)
            MediaPlayer.create(context, R.raw.pencil).start()

        gameCells.value.flatten().find { it.isActive }?.isActive = false
        cell.isActive = true
    }

    private fun changeCellOwner(
        cell: TitunganCell
    ) {


        if (cell.owner == null && isGameStarted.value) {
            Log.d("GameState", "Changing cell owner")
            cell.owner = currentPlayer.value

            Log.d("GameState", "Cell owner changed: ${cell.x}, ${cell.y}")

            changePlayer()
        } else {
            Log.i("StatusPlayer", (cell.owner != null).toString())
        }
    }
    private fun prepareGameRules() {
        //harusny pake data store
        gameSize.intValue = gameDefaultSize
    }

    private fun prepareGameLogic() {
        gameLogic = SimpleGameLogic(gameCells.value, gameSize.intValue)
    }

    fun getRandomNumber(jumlahCells : Int = 0) : MutableList<Int>{
        var angkaRandom  = mutableListOf<Int>()

        for (i in 1..jumlahCells * jumlahCells) {
            angkaRandom.add(Random.nextInt(0, 100))
        }
        return angkaRandom
    }

    private fun getEmptyBoard(): List<List<TitunganCell>> {
        val columns = mutableListOf<List<TitunganCell>>()
        val angkaRandom = getRandomNumber(gameSize.intValue)
        var iterator = 0
        for (x in 0 until gameSize.intValue) {
            val row = mutableListOf<TitunganCell>()
            for (y in 0 until gameSize.intValue) {
                row.add(TitunganCell(x, y, angkaRandom.get(iterator)))
                iterator++
            }
//
            columns.add(row)
        }
        return columns
    }

    private fun preparePlayers() {
        val firstPlayerName = context.getString(R.string.first_player_default_name)
        val secondPlayerName =  context.getString(R.string.second_player_default_name)

        val firstPlayerShape = XShape
        val secondPlayerShape = RingShape

//        val firstPlayerDice = Random.nextInt(Constants.diceRange)
//        val secondPlayerDice = Random.nextInt(Constants.diceRange)

        players.value = createPlayers(
            firstPlayerName,
            firstPlayerShape,
            secondPlayerShape,
            secondPlayerName
        )

        currentPlayer.value = players.value.first()
    }

    //fungsi setFirstPlayerToDiceWinner

    private fun createPlayers(
        firstPlayerName: String,
        firstPlayerShape: Shape,
        secondPlayerShape: Shape,
        secondPlayerName: String
    ) = buildList {
        add(Player(firstPlayerName, firstPlayerShape.toName()))
        add(Player(secondPlayerName,secondPlayerShape.toName()))
    }

    //fungsi dummy dice rolling
    fun getOwnerShape(
        owner: Player?
    ): Shape {
        return if (owner == players.value.first()) owner.shape?.toShape() ?: XShape
        else owner?.shape?.toShape() ?: RingShape
    }

    fun addScore(
//        player: Player,
    ) {
        Log.i("Player Turn", "Before is ${currentPlayer.value?.name} turn")

        currentPlayer.value?.score = currentPlayer.value?.score!! + 1
        changePlayer()

        Log.i("Player score", "Score player 1 : ${players.value.get(0).score}")
        Log.i("Player Turn", "Now is ${currentPlayer.value?.name} turn")
    }
    private fun changePlayer() {
        Log.i("Player Turn Info", "${currentPlayer.value}")


        if (currentPlayer.value == players.value.first()) {
            currentPlayer.value = players.value.last()
            Log.i("blals","true")
        }
        else {
            currentPlayer.value = players.value.first()
            Log.i("blals","false")
        }

        Log.i("Player Turn Info", "${currentPlayer.value}")

    }



    private fun checkIfGameIsFinished() {
        winner.value = findWinner()
        if (winner.value != null)
            finishGame()
//        if (gameLogic?.isGameDrew() == true)
//            handleDrewGame()
    }

//    private fun handleDrewGame() {
//        finishGame()
//        isGameDrew.value = true
//    }

    private fun finishGame() {
        isGameFinished.value = true
    }

    private fun findWinner(): Player? {
        return gameLogic?.findWinner()
    }

     fun countResult (
        number1 : Int = numberInput1.value.toInt(),
        number2 : Int = numberInput2.value.toInt(),
        operator: String = selectedOperator.value
    ) : Int{
        return when(operator) {
            "+" -> number1 + number2
            "-" -> number1 - number2
            "x" -> number1 * number2
            "/" -> number1 / number2
            else -> {0}
        }
    }

    fun checkResult(
        result: Int? = gameCells.value.flatten().find { it.isActive }?.number
    ) : Boolean {
        return countResult() == result
    }


}

@Composable
fun rememberHomeState(
    hapticFeedback: HapticFeedback = LocalHapticFeedback.current,
    context: Context = LocalContext.current,
    titunganCell: MutableState<List<List<TitunganCell>>> = rememberSaveable { mutableStateOf(emptyList()) },
    gameSize: MutableIntState = rememberSaveable { mutableIntStateOf(gameDefaultSize) },
    currentPlayer: MutableState<Player?> = rememberSaveable { mutableStateOf(null) },
    players: MutableState<List<Player>> = rememberSaveable { mutableStateOf(listOf()) },
    isGameStarted: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    isGameFinished: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    winner: MutableState<Player?> = rememberSaveable { mutableStateOf(null) },
    numberInput1: MutableState<String> = rememberSaveable {
        mutableStateOf("")
    },
    numberInput2: MutableState<String> = rememberSaveable {
        mutableStateOf("")
    },
    selectedOperator: MutableState<String> = rememberSaveable {
        mutableStateOf("+")
    }
//    isGameDrew: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
//    isRollingDices: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
//        firstPlayerPolicy: MutableState<FirstPlayerPolicy> = rememberSaveable {
//            mutableStateOf(
//                FirstPlayerPolicy.DiceRolling
//            )
//        },
) = remember(
    hapticFeedback,
    context,
    titunganCell,
    gameSize,
    currentPlayer,
    players,
    isGameStarted,
    isGameFinished,
    winner,
    numberInput1,
    numberInput2,
    selectedOperator
) {
    GameState(
        hapticFeedback,
        context,
        titunganCell,
        gameSize,
        currentPlayer,
        players,
        isGameStarted,
        isGameFinished,
        winner,
//            isRollingDices,
//            firstPlayerPolicy,
        numberInput1,
        numberInput2,
        selectedOperator
    )
}

