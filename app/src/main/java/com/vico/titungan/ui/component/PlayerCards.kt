package com.vico.titungan.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ContentAlpha
import com.vico.titungan.R
import com.vico.titungan.model.Player
import com.vico.titungan.ui.theme.TitunganTheme

//@Preview(showBackground = true)
//@Composable
//fun PlayerCardsPreview() {
//    var navController: NavHostController = rememberNavController()
//
//    TitunganTheme() {
//        var player1 = Player("First Player", PointType.O)
//        var player2 = Player("Second Player", PointType.X)
//
//        var players = buildList {
//            add(player1)
//            add(player2)
//        }
//
//        PlayerCards(players, player1)
//    }
//}
@Composable
internal fun PlayerCards(
    players: List<Player>,
    currentPlayer: Player?
) {
    val firstPlayer = players[0]
    val secondPlayer = players[1]

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PlayerCard(
            modifier = Modifier.weight(1f),
            player = firstPlayer,
            isCurrentPlayer = firstPlayer == currentPlayer
        )
        PlayerCard(
            modifier = Modifier.weight(1f),
            player = secondPlayer,
            isCurrentPlayer = secondPlayer == currentPlayer
        )
    }
}
@Composable
internal fun PlayerCard(
    modifier: Modifier = Modifier,
    player: Player,
    isCurrentPlayer: Boolean = true
) {
    val alpha = if (isCurrentPlayer) ContentAlpha.high else ContentAlpha.disabled

    OutlinedCard(
        modifier = modifier.alpha(alpha)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            if (firstPlayerPolicy == FirstPlayerPolicy.DiceRolling && isFontScaleNormal()) {
//                AnimatedContent(
//                    targetState = player.diceIndex,
//                    label = "",
//                    content = { PlayerDice(diceIndex = it) },
//                    transitionSpec = {
//                        (slideInVertically { it } + fadeIn())
//                            .togetherWith(slideOutVertically { -it } + fadeOut())
//                    }
//                )
//            }
            player.shape?.toShape()?.let { shape -> ShapePreview(shape, 30.dp) }

            Text(
                text = player.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displayLarge,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun PlayerPointType(
    player: Player
) {
    val imageRes = remember(player.shape) {
        when (player.shape) {
            (player.shape == null ).toString() -> R.drawable.transparent
            (player.shape == "xShape").toString() -> R.drawable.ic_tic_tac_toe_x
            (player.shape == "circleShape").toString() -> R.drawable.ic_tic_tac_toe_o
            (player.shape == "ringShape").toString() -> R.drawable.ic_tic_tac_toe_o
            else -> {}
        }
    }

//    val pointTypeBackground by animateColorAsState(
//        targetValue = if (playerTurn) Color(0xFFffd8e8)
//        //light_onFlirtContainer
//        else MaterialTheme.colorScheme.background,
//        animationSpec = tween(500), label = ""
//    )

    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
    ) {
        Image(
            painter = painterResource(
                id = when (player.shape) {
                    (player.shape == null ).toString() -> R.drawable.transparent
                    (player.shape == "xShape").toString() -> R.drawable.ic_tic_tac_toe_x
                    (player.shape == "circleShape").toString() -> R.drawable.ic_tic_tac_toe_o
                    (player.shape == "ringShape").toString() -> R.drawable.ic_tic_tac_toe_o
                    else -> {R.drawable.transparent}
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 4.dp
                )
                .size(24.dp))
    }
}