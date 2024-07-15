package com.vico.titungan.ui.screen.game

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import com.vico.titungan.model.Player
import com.vico.titungan.model.TitunganCell
import com.vico.titungan.ui.component.RingShape
import com.vico.titungan.ui.component.XShape
import com.vico.titungan.ui.component.toName
import com.vico.titungan.ui.component.toShape
import com.vico.titungan.ui.screen.game.GameConstants.gameDefaultSize
import io.github.yamin8000.dooz.game.logic.GameLogic
import kotlin.math.abs
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
    var showSnackbar : MutableState<Boolean>,
    var isHasChance : MutableState<Boolean>,
    var showWinnerDialog : MutableState<Boolean>,
    var showExitConfirmation : MutableState<Boolean>,

    ) {

    private var isSoundOn = true
    private var isVibrationOn = true
    private var gameLogic: GameLogic? = null
    private var tutupTiles = false
//    private val dataStore = DataStoreHelper(context.settings)

    init {
        isSoundOn = true
        isVibrationOn = true
//        prepareGame()

//        newGame("Vico", "Ridho", 5, 3, 1, 0, 0,  arrayOf("+", "-"), 1)
    }

    fun newGame(
        player1 : String,
        player2 : String,
        nyawa : Int,
        tiles : Int,
        listOperators : Array<String>,
        playOrder : Int,
        tutupTiles : Boolean
    )  {

        prepareGameRules(tiles, listOperators)

        this.tutupTiles = tutupTiles
        resetGame(listOperators)
        preparePlayers(player1, player2, nyawa, playOrder)
//        prepareGameLogic()
//        prepareGame()
        isGameStarted.value = true
    }
    private fun prepareGameRules(size: Int, listOperators: Array<String>) {
        //harusny pake data store
        gameSize.intValue = size
        selectedOperator.value = listOperators.first()
    }

    private fun resetGame(listOperators: Array<String>) {
        winner.value = null
        isGameFinished.value = false
        isGameStarted.value = false
        gameCells.value = getEmptyBoard(listOperators)
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

        currentPlayer.value = if(playOrder == 1) players.value.first() else if(playOrder == 2) players.value.last() else players.value[Random.nextInt(0, 2)]
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


    fun checkIsAllOwnersNotNull() : Boolean {
        return gameCells.value.flatten().all { it.owner != null }
    }

    fun getHigherScorePlayer(): Player {
        return if (players.value.first().score >= players.value.last().score) players.value.first() else players.value.last()
    }

    fun checkIfAllTilesIsFull() : Boolean{
        return players.value.first().score + players.value.last().score == gameSize.intValue * gameSize.intValue
    }

    fun checkIsRightAnswer(
        caraMenang : Int,
        defisitSkor : Int,
        maksimumSkor : Int,
    ) {
        if(checkResult()) {
            addScore()
            if(caraMenang == 1 || caraMenang == 2) {
                //cek dulu apakah semua tiles sudah penuh
                if(checkIfAllTilesIsFull()) winner.value = getHigherScorePlayer()

                //cek apakah defisit skornyo
                else if (caraMenang == 2 && abs(players.value.first().score - players.value.last().score) == defisitSkor) winner.value = getHigherScorePlayer()

            } else {
                if(players.value.first().score + players.value.last().score == maksimumSkor) winner.value = getHigherScorePlayer()
            }

            if(winner.value != null) showWinnerDialog.value = true

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

    fun checkResult(
        result: Int? = activeCell.value?.number
    ) : Boolean {
        Log.i("Result", "$result")
        return countResult() == result
    }

    fun changePlayerLive() {
        currentPlayer.value?.life = currentPlayer.value?.life!! - 1
        if (currentPlayer.value?.life == 0) {
            winner.value = if(currentPlayer.value == players.value[0]) players.value[1] else players.value[0]

            showWinnerDialog.value = true

            //waktunyo reset
            isPlayerInputRightValue.value = true

            //hapus inputan
            numberInput1.value = ""
            numberInput2.value = ""
            selectedOperator.value = "+"

            activeCell.value = null
        }


    }

    fun checkWin() {

    }

    fun changeActiveCell(cell: TitunganCell) {
        activeCell.value = cell

        if(tutupTiles) {
            cell.isClosed = false
            isHasChance.value = false
        }
    }

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

    fun getRandomNumber(jumlahCells : Int = 0, listOperators: Array<String>, min : Int = 11, max : Int = 100) : MutableList<Int>{
        val angkaRandom  = mutableListOf<Int>()
        var angkaRandomTemp = 0

        if(listOperators.size <= 2 && (!listOperators.contains("+") && !listOperators.contains("-"))) {
            while (angkaRandom.size < jumlahCells * jumlahCells) {
                angkaRandomTemp = Random.nextInt(min, max)

                if(!isPrime(angkaRandomTemp) ) angkaRandom.add(angkaRandomTemp)
            }
        } else {
            for (i in 1..jumlahCells * jumlahCells) {
                angkaRandom.add(Random.nextInt(min, max))
            }
        }

        return angkaRandom
    }

    fun isPrime(num: Int): Boolean {
        if (num < 2) return false
        for (i in 2 until num) {
            if (num % i == 0) {
                return false
            }
        }
        return true
    }

    private fun getEmptyBoard(listOperators : Array<String>): List<List<TitunganCell>> {
        Log.i("Marghafsssf", "iyaa")

        val columns = mutableListOf<List<TitunganCell>>()
        val angkaRandom = getRandomNumber(gameSize.intValue, listOperators)
        var iterator = 0
        for (x in 0 until gameSize.intValue) {
            val row = mutableListOf<TitunganCell>()
            for (y in 0 until gameSize.intValue) {
                if (tutupTiles) row.add(TitunganCell(x = x, y = y, number = angkaRandom.get(iterator), isClosed = true))
                else row.add(TitunganCell(x = x, y = y, number = angkaRandom.get(iterator)))
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
        if (currentPlayer.value == players.value.first()) currentPlayer.value = players.value.last()
        else currentPlayer.value = players.value.first()

        if(tutupTiles) isHasChance.value = true

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
    showSnackbar: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    isHasChance: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    showWinnerDialog: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    showExitConfirmation: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
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
    showSnackbar,
    isHasChance,
    showWinnerDialog,
    showExitConfirmation
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
        numberInput1,
        numberInput2,
        selectedOperator,
        activeCell,
        isTimerRunning,isPlayerInputRightValue,
        showSnackbar,
        isHasChance,
        showWinnerDialog,
        showExitConfirmation
    )
}

