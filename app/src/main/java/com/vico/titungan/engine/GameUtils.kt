package com.vico.titungan.engine

object GameUtils {
    const val PLAYER_X = "X"
    const val PLAYER_O = "O"

    fun isBoardFull(board: ArrayList<String>): Boolean {
        for (i in board) {
            if (i != PLAYER_X && i != PLAYER_O) return false
        }
        return true
    }

    fun isGameWon(board: ArrayList<String>, player: String): Boolean =
        //check rows
        if (board[0] == player && board[1] == player && board[2] == player) true
        else if (board[3] == player && board[4] == player && board[5] == player) true
        else if (board[6] == player && board[7] == player && board[8] == player) true

        //check columns
        else if (board[0] == player && board[3] == player && board[6] == player) true
        else if (board[1] == player && board[4] == player && board[7] == player) true
        else if (board[2] == player && board[5] == player && board[8] == player) true

        //check diagonals
        else if (board[2] == player && board[4] == player && board[6] == player) true
        else board[0] == player && board[4] == player && board[8] == player

    fun gameResult(board: ArrayList<String>, singleMode: Boolean): String {
        when {
            isGameWon(board, PLAYER_X) -> return "${if (singleMode) "YOU" else "PLAYER X"} Won"
            isGameWon(board, PLAYER_O) -> return "${if (singleMode) "COMPUTER" else "PLAYER O"} Won"
            isBoardFull(board) -> return "It is Tie"
        }
        return "Tie"
    }
}