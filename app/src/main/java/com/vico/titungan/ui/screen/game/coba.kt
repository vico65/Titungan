import android.os.CountDownTimer
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.vico.titungan.ui.component.AnimatingText
import kotlinx.coroutines.delay

@Composable
fun Clock(
    modifier: Modifier = Modifier
) {
    var totalTime = 30
    var isRunning = true
    var timeLeft by remember { mutableIntStateOf(totalTime) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            for (second in totalTime downTo 0) {
                timeLeft = second

                delay(1000L)
            }

            //waktu dio abes
            if (timeLeft == 0) {
                timeLeft = totalTime
            }
        }
    }

    Row(
        modifier = modifier
            .width(60.dp)
            .clip(RoundedCornerShape(100))
            .background(color = MaterialTheme.colorScheme.background)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(100),
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        AnimatingText(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f),
            time = timeLeft.toString()
        )
    }

//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .background(color = Color(0xFF01579B), shape = CircleShape)
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "$timeLeft",
//            style = MaterialTheme.typography.headlineMedium.copy(
//                fontWeight = FontWeight.Bold,
//                color = Color.White,
//                fontSize = 24.sp
//            )
//        )
//    }
}

@Preview
@Composable
fun clockPreview() {
    Clock()
}

//@ExperimentalAnimationApi
//private fun getContentTransformAnim(): ContentTransform {
//
//    val topOffset = -100
//
//    val enterAnim = slideIn(
//        initialOffset = { IntOffset(x = 0, y = topOffset) },
//        animationSpec = tween(
//            durationMillis = 300,
//            delayMillis = 310
//        ),
//    ) + fadeIn(
//        animationSpec = tween(
//            durationMillis = 300,
//            delayMillis = 310,
//        ),
//    )
//
//    val exitAnim = slideOut(
//        targetOffset = { IntOffset(x = 0, y = topOffset) },
//        animationSpec = tween(durationMillis = 300)
//    ) + fadeOut(
//        animationSpec = tween(durationMillis = 300),
//    )
//
//    return ContentTransform(enterAnim, exitAnim)
//}


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
