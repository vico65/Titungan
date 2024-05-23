package com.vico.titungan.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vico.titungan.ui.component.PlayerItem
import com.vico.titungan.R
import com.vico.titungan.data.PointType
import com.vico.titungan.data.TurnType
import com.vico.titungan.model.Player
import com.vico.titungan.ui.component.RoundItem
import com.vico.titungan.ui.component.TicTacToeBoard

@SuppressLint("UnrememberedMutableState")
@Composable
fun GameScreen(
    navController: NavController,
//    viewModel: GameViewModel
) {
//	LaunchedEffect(viewModel.winner) {
//		when (viewModel.winner) {
//			WinType.Tie -> "Tie".toast()
//			WinType.O -> {
//				if (viewModel.playerOne.pointType == PointType.O) "${viewModel.playerOne.name} win".toast()
//				else "${viewModel.playerTwo.name} win".toast()
//			}
//			WinType.X -> {
//				if (viewModel.playerOne.pointType == PointType.X) "${viewModel.playerOne.name} win".toast()
//				else "${viewModel.playerTwo.name} win".toast()
//			}
//			WinType.None -> {}
//		}
//

//		viewModel.clearBoard()
//	}

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            var playerOne by mutableStateOf(Player.Player1)
            var playerTwo by mutableStateOf(Player.Player2)
            var currentTurn by mutableStateOf(TurnType.PlayerOne)
            var round by mutableStateOf(1)
            var draw by mutableStateOf(0)
            val board = mutableStateListOf<List<PointType>>()

            PlayerItem(
                player = playerOne,
                playerTurn = currentTurn == TurnType.PlayerOne
            )

            RoundItem(
                round = round,
                draw = draw
            )

            PlayerItem(
                player = playerTwo,
                playerTurn = currentTurn == TurnType.PlayerTwo
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
        ) {
            TicTacToeBoard(
                board = board,
                onClick = { row, col ->
//                    updateBoard(row, col)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f / 1f)
            )
        }
    }
}
