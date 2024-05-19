package com.vico.titungan

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vico.titungan.engine.GameUtils
import com.vico.titungan.engine.GameUtils.PLAYER_O
import com.vico.titungan.engine.GameUtils.PLAYER_X
import com.vico.titungan.engine.GameUtils.isBoardFull
import com.vico.titungan.engine.GameUtils.isGameWon

class MainViewModel : ViewModel() {

    var singlePlayer by mutableStateOf(true)
        private set

    var isGameOver by mutableStateOf(false)
        private set

    var winner by mutableStateOf("")
        private set

    var board by mutableStateOf(arrayListOf("", "", "", "", "", "", "", "", ""))
        private set

    private var currentPlayer = PLAYER_X

    fun play(move: Int) {
        if (isGameOver) return

        if (board[move] == "") {
            if (currentPlayer == PLAYER_X) {
                board = ArrayList(board.toMutableList().also {
                    it[move] = PLAYER_X
                })
                currentPlayer = PLAYER_O

//                if (singlePlayer) {
//                    if (!isBoardFull(board) && !isGameWon(board, PLAYER_X)) {
//                        val nextMove = GameUtils.computerMove(board)
//
//                        board = ArrayList(board.toMutableList().also {
//                            it[nextMove] = PLAYER_O
//                        })
//                    }
//                    currentPlayer = PLAYER_X
//                }

            } else {
                board = ArrayList(board.toMutableList().also {
                    it[move] = PLAYER_O
                })
                currentPlayer = PLAYER_X

//                if (singlePlayer) {
//                    if (!isBoardFull(board) && !isGameWon(board, PLAYER_O)) {
//                        val nextMove = GameUtils.computerMove(board)
//
//                        board = ArrayList(board.toMutableList().also {
//                            it[nextMove] = PLAYER_X
//                        })
//                    }
//                    currentPlayer = PLAYER_O
//                }
            }
        }


        //calculate and show game result
        isGameOver = isGameWon(board, PLAYER_X) || isGameWon(board, PLAYER_O) || isBoardFull(board)
        winner = GameUtils.gameResult(board, singlePlayer)

        Log.d(TAG, "$board")
    }

    fun reset() {
        isGameOver = false
        board = arrayListOf("", "", "", "", "", "", "", "", "")
    }
}