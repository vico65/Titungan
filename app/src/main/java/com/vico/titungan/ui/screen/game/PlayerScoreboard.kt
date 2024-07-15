package com.vico.titungan.ui.screen.game

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ContentAlpha
import com.vico.titungan.model.Player
import com.vico.titungan.ui.component.ShapePreview
import com.vico.titungan.ui.component.XShape
import com.vico.titungan.ui.component.toShape
import com.vico.titungan.ui.theme.DarkRed
import com.vico.titungan.ui.theme.Green
import com.vico.titungan.ui.theme.Salmon
import com.vico.titungan.ui.theme.TitunganTheme

@Preview
@Composable
fun ClockPreview() {

    TitunganTheme() {
        val players = listOf(Player(name = "Vico", score = 0, life = 3), Player(name = "Ridho", score = 0, life = 2))
        ScoreBoard(players = players, currentPlayer = players[1])
    }
}
@Composable
internal fun ScoreBoard(
    players: List<Player>,
    currentPlayer: Player?
) {
    var firstPlayer: Player? = null
    var secondPlayer: Player? = null

    if (players.isNotEmpty()) {
        firstPlayer = players[0]
        secondPlayer = players[1]
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (firstPlayer != null) {
                PlayerScore(
                    player = firstPlayer,
                    isCurrentPlayer = firstPlayer == currentPlayer,
                    isFirst = true,
                )
            }
            Text(
                text = "vs",
                color = Color.Gray,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            if (secondPlayer != null) {
                PlayerScore(
                    player = secondPlayer,
                    isCurrentPlayer = secondPlayer == currentPlayer
                )
            }


        }

    }


}

@SuppressLint("SuspiciousIndentation")
@Composable
internal fun PlayerScore(
    modifier : Modifier = Modifier,
    player: Player,
    isCurrentPlayer: Boolean = true,
    isFirst : Boolean = false
) {
    val alpha = if (isCurrentPlayer) ContentAlpha.high else ContentAlpha.disabled


        Row(
            modifier = modifier
                .animateContentSize()
                .alpha(alpha),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            if(isFirst) {
                PlayerNamePanel(player = player)
                Spacer(modifier = Modifier.width(8.dp))
                playerSkorPanel(player = player)
            } else {
                playerSkorPanel(player = player)
                Spacer(modifier = Modifier.width(8.dp))
                PlayerNamePanel(player = player)
            }
        }





}

@Composable
fun PlayerNamePanel(
    player : Player
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp))
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(100),
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                )
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                player.shape?.toShape()?.let { shape ->
                    if (shape == XShape) ShapePreview(shape, 20.dp, Green)
                    else ShapePreview(shape, 18.dp, Salmon)
                }

                Text(
                    text = player.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

        }

        PlayerLives(playerLives = player.life)
    }
}

@Composable
fun playerSkorPanel(
    player : Player,
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(MaterialTheme.colorScheme.background, CircleShape)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(100),
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = player.score.toString(),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PlayerLives(playerLives: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(8.dp)
    ) {
        repeat(playerLives) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = DarkRed,
                modifier = Modifier.size(
                    if(playerLives > 2) ((80 / playerLives) - (10 - (2 * playerLives))).dp
                    else 27.dp
                )
            )
            Spacer(modifier = Modifier.width(3.dp))
        }
    }
}


