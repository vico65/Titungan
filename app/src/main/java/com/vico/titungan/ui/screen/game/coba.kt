import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
fun CountdownTimer(
    totalTime: Int = 30, // Total countdown time in seconds
    modifier: Modifier = Modifier,
    onTimeUp: () -> Unit = {}
) {
    var timeLeft by remember { mutableStateOf(totalTime) }
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            for (second in totalTime downTo 0) {
                timeLeft = second
                delay(1000L)
            }
            onTimeUp()
            isRunning = false
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = Color(0xFF01579B), shape = CircleShape)
            .padding(16.dp)
    ) {
        Text(
            text = "$timeLeft",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp
            )
        )
    }
}

@Preview
@Composable
fun CountdownTimerPreview() {
    CountdownTimer(
        totalTime = 30,
        modifier = Modifier.padding(16.dp)
    )
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
