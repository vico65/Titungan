package com.vico.titungan

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vico.titungan.ui.theme.TitunganTheme
import com.vico.titungan.ui.theme.purple500

//import com.vico.titungan.ui.TitunganTheme


class MainActivity : ComponentActivity() {
    val mainViewModel by viewModels<MainViewModel>()


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TitunganTheme {
                Scaffold (topBar = {
                    TitunganAppBar(mainViewModel.singlePlayer) {

                    }
                }){
                    Surface (color = MaterialTheme.colorScheme.background) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            ButtonGrid(board = mainViewModel.board, onclick = mainViewModel::play )

                            if (mainViewModel.isGameOver) {
                                Box{
                                    Text(
                                        text = "Game is Over : ${mainViewModel.winner}",
                                        fontSize = 20.sp
                                    )
                                }
                            }

                            ResetButton(mainViewModel::reset)

                            TextButton(
                                onClick = {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Coming Soon",
                                        Toast.LENGTH_LONG
                                    ).show()
                                },
                                modifier = Modifier.padding(16.dp).height(50.dp)
                            ) {
                                Text(
                                    text = "Play With a Friend",
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

//    @SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Preview(showBackground = true)
//    @Composable
//    fun GreetingPreview() {
//        TitunganTheme {
//            S
//        }
//    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun TicTacToeButton(text: String, onclick: () -> Unit) {
    Box(modifier = Modifier.padding(8.dp)) {
        TextButton(
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, purple500),
            onClick = onclick,
            enabled = text.isBlank()
        ) {
            Text(
                text = text,
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
fun ButtonGrid(board: ArrayList<String>, onclick: (Int) -> Unit) {
    Column(verticalArrangement = Arrangement.SpaceEvenly) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            TicTacToeButton(text = board[0]) { onclick(0) }
            TicTacToeButton(text = board[1]) { onclick(1) }
            TicTacToeButton(text = board[2]) { onclick(2) }
        }

        Row(horizontalArrangement = Arrangement.SpaceAround) {
            TicTacToeButton(text = board[3]) { onclick(3) }
            TicTacToeButton(text = board[4]) { onclick(4) }
            TicTacToeButton(text = board[5]) { onclick(5) }
        }
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            TicTacToeButton(text = board[6]) { onclick(6) }
            TicTacToeButton(text = board[7]) { onclick(7) }
            TicTacToeButton(text = board[8]) { onclick(8) }
        }
    }
}



