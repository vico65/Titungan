package com.vico.titungan.ui.screen.game

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentCompositionErrors
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
    var selectedOperator: MutableState<String>,
    var activeCell: MutableState<TitunganCell?>,
    var isTimerRunning: MutableState<Boolean>,
    var isPlayerInputRightValue: MutableState<Boolean>,
    var showSnackbar : MutableState<Boolean>

    ) {

    private var isSoundOn = true
    private var isVibrationOn = true
    private var gameLogic: GameLogic? = null
//    private val dataStore = DataStoreHelper(context.settings)

    init {
        isSoundOn = true
        isVibrationOn = true
//        prepareGame()
    }

    fun newGame(
        player1 : String,
        player2 : String,
        nyawa : Int,
        tiles : Int,
        caraMenang : Int,
        defisitSkor : Int,
        maksimumSkor : Int,
        listOperators : Array<String>,
        playOrder : Int
    )  {
        resetGame()
        prepareGameRules(tiles)
        preparePlayers(player1, player2, nyawa, playOrder)
//        prepareGameLogic()
//        prepareGame()
        isGameStarted.value = true
    }
    private fun prepareGameRules(size : Int) {
        //harusny pake data store
        gameSize.intValue = size
    }

    private fun preparePlayers(player1 : String, player2 : String, nyawa: Int, playOrder: Int) {
        val firstPlayerName = player1
        val secondPlayerName =  player2

        val firstPlayerShape = XShape
        val secondPlayerShape = RingShape

        players.value = createPlayers(
            firstPlayerName,
            firstPlayerShape,
            secondPlayerShape,
            secondPlayerName,
            nyawa
        )

        currentPlayer.value = if(playOrder == 1) players.value.first() else players.value.last()
    }

    private fun createPlayers(
        firstPlayerName: String,
        firstPlayerShape: Shape,
        secondPlayerShape: Shape,
        secondPlayerName: String,
        nyawa: Int
    ) = buildList {
        add(Player(name = firstPlayerName,shape = firstPlayerShape.toName(),life = nyawa))
        add(Player(name = secondPlayerName,shape = secondPlayerShape.toName(), life = nyawa))
    }

//    private fun prepareGame() {
//        resetGame()
//        prepareGameRules()
//        preparePlayers()
//        prepareGameLogic()
//    }

    private fun resetGame() {
        winner.value = null
        isGameFinished.value = false
        isGameStarted.value = false
        gameCells.value = getEmptyBoard()
    }

    fun checkWinStatus() {
        if(checkResult()) {
            addScore()
            activeCell.value?.owner = currentPlayer.value
        } else {
            currentPlayer.value?.life = currentPlayer.value?.life!! - 1
            showSnackbar.value = true
        }

        //waktunyo reset
        isPlayerInputRightValue.value = true

        //hapus inputan
        numberInput1.value = ""
        numberInput2.value = ""
        selectedOperator.value = "+"

        activeCell.value = null
        changePlayer()
    }

    fun changeActiveCell(cell: TitunganCell) {activeCell.value = cell}

    private fun addScore() {currentPlayer.value?.score = currentPlayer.value?.score!! + 1}

    private fun countResult (
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
        result: Int? = activeCell.value?.number
    ) : Boolean {
        Log.i("Result", "$result")
        return countResult() == result
    }

//    private fun prepareGameLogic() {
//        gameLogic = SimpleGameLogic(gameCells.value, gameSize.intValue)
//    }

    fun getRandomNumber(jumlahCells : Int = 0) : MutableList<Int>{
        val angkaRandom  = mutableListOf<Int>()

        for (i in 1..jumlahCells * jumlahCells) {
            angkaRandom.add(Random.nextInt(11, 100))
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

    //fungsi dummy dice rolling
    fun getOwnerShape(
        owner: Player?
    ): Shape {
        return if (owner == players.value.first()) owner.shape?.toShape() ?: XShape
        else owner?.shape?.toShape() ?: RingShape
    }


    fun changePlayer() {
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
    },
    activeCell: MutableState<TitunganCell?> = rememberSaveable { mutableStateOf(null) },
    isTimerRunning: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    isPlayerInputRightValue: MutableState<Boolean> = rememberSaveable {mutableStateOf(false)},
    showSnackbar: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
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
    selectedOperator,
    activeCell,
    isTimerRunning,
    showSnackbar
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
        selectedOperator,
        activeCell,

        isTimerRunning,isPlayerInputRightValue,
        showSnackbar
    )
}

