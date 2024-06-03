import android.os.CountDownTimer
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay


@Composable
fun LivesInfo(playerLives: List<Int>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Lives",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            playerLives.forEachIndexed { index, lives ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(lives) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    if (index < playerLives.size - 1) {
                        Spacer(modifier = Modifier.width(32.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CountdownTimerPreview() {
    LivesInfo(listOf(1,3))
}

//@Composable
//internal fun PlayerCard(
//    modifier: Modifier = Modifier,
//    player: Player,
//    isCurrentPlayer: Boolean = true
//) {
//    val alpha = if (isCurrentPlayer) ContentAlpha.high else ContentAlpha.disabled
//
////    val backgroundColor = if (isCurrentPlayer) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
////    val contentColor = if (isCurrentPlayer) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
//
//    OutlinedCard(
//        modifier = modifier
//            .alpha(alpha)
//            .animateContentSize()
//    ) {
//        Column(
//            modifier = Modifier.padding(14.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
////            if (firstPlayerPolicy == FirstPlayerPolicy.DiceRolling && isFontScaleNormal()) {
////                AnimatedContent(
////                    targetState = player.diceIndex,
////                    label = "",
////                    content = { PlayerDice(diceIndex = it) },
////                    transitionSpec = {
////                        (slideInVertically { it } + fadeIn())
////                            .togetherWith(slideOutVertically { -it } + fadeOut())
////                    }
////                )
////            }
//
//                player.shape?.toShape()?.let { shape -> ShapePreview(shape, 30.dp) }
//
//                Text(
//                    text = player.name,
//                    color = MaterialTheme.colorScheme.primary,
//                    style = MaterialTheme.typography.displayLarge,
//                    fontSize = 16.sp
//                )
//            }
//        }
//
//    }
//}
