package com.vico.titungan.ui.component

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ContentAlpha
import com.vico.titungan.model.Player
import com.vico.titungan.ui.theme.fontFamilyFredoka

@Composable
internal fun ScoreBoard(
    players: List<Player>,
    currentPlayer: Player?
) {
    val firstPlayer = players[0]
    val secondPlayer = players[1]

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PlayerScore(
            player = firstPlayer,
            playerColor = Color(0xFF64B5F6), // Blue color
            scoreColor = Color.White,
            isCurrentPlayer = firstPlayer == currentPlayer
        )
        Text(
            text = "vs",
            color = Color.Gray,
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        PlayerScore(
            player = secondPlayer,
            playerColor = Color(0xFFE57373), // Red color
            scoreColor = Color.White,
            isCurrentPlayer = secondPlayer == currentPlayer
        )
    }
}

@Composable
internal fun PlayerScore(
    modifier : Modifier = Modifier,
    player: Player,
    playerColor: Color,
    scoreColor: Color,
    isCurrentPlayer: Boolean = true
) {
    val alpha = if (isCurrentPlayer) ContentAlpha.high else ContentAlpha.disabled

    Row(
        modifier = modifier
            .animateContentSize()
            .alpha(alpha),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if(player.name == "Player 1") {
            playerNamePanel(playerColor = playerColor, player = player)
            Spacer(modifier = Modifier.width(8.dp))
            playerSkorPanel(playerColor = playerColor, player = player, scoreColor = scoreColor)
        } else {
            playerSkorPanel(playerColor = playerColor, player = player, scoreColor = scoreColor)
            Spacer(modifier = Modifier.width(8.dp))
            playerNamePanel(playerColor = playerColor, player = player)

        }
    }
}

@Composable
fun playerNamePanel(
    playerColor: Color,
    player : Player
) {
    Box(
        modifier = Modifier
            .background(playerColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            player.shape?.toShape()?.let { shape -> ShapePreview(shape, 20.dp) }

            Text(
                text = player.name,
                color = Color.White,
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge.copy(fontFamily = fontFamilyFredoka),
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
fun playerSkorPanel(
    playerColor: Color,
    player : Player,
    scoreColor: Color
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(playerColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = player.score.toString(),
            color = scoreColor,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}


