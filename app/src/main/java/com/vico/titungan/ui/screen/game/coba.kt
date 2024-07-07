import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.vico.titungan.R
import com.vico.titungan.ui.component.RingShape
import com.vico.titungan.ui.component.ShapePreview
import com.vico.titungan.ui.component.XShape
import com.vico.titungan.ui.component.button.ColoredButton
import com.vico.titungan.ui.component.button.HighlightedRoundedButton
import com.vico.titungan.ui.component.button.RoundedButton
import com.vico.titungan.ui.screen.game.GameConstants
import com.vico.titungan.ui.theme.Green
import com.vico.titungan.ui.theme.Salmon
import com.vico.titungan.ui.theme.TitunganTheme
import com.vico.titungan.ui.theme.yellow

@Preview
@Composable
fun ClockPreview() {

//    TitunganTheme() {
//        Coba("Vico")
//    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Coba(
    nama : Array<String>
) {
    Log.i("nama", nama.toString())
}

@Composable
fun GameButtons(
    modifier: Modifier = Modifier,
    canResume: State<Boolean>,
    onStartClicked: () -> Unit,
    onResumeClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        RoundedButton(
            modifier = Modifier
                .wrapContentSize(),
            text = "New game",
            onClick = onStartClicked,
        )

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            visible = canResume.value,
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                HighlightedRoundedButton(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = "Resume",
                    onClick = onResumeClicked,
                )
            }
        }

        Spacer(
            modifier = Modifier.weight(1f),
        )

        RoundedButton(
            modifier = Modifier
                .wrapContentSize(),
            text = "Settings",
            onClick = onSettingsClicked,
        )

        val padding = 20.dp + 32.dp

        Spacer(
            modifier = Modifier.height(padding),
        )
    }
}

@Composable
internal fun DifficultyItem(
    modifier: Modifier = Modifier,
    difficulty: DifficultyItem,
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // Title
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(),
                text = difficulty.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium
                ),
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 10 x 10
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .wrapContentSize(),
                text = difficulty.gridMessage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                ),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .wrapContentSize()
                        .align(alignment = Alignment.CenterVertically),
                    text = difficulty.difficulty.mines.toString(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    ),
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.error)
                        .padding(4.dp),
                    painter = painterResource(id = R.drawable.bolt),
                    tint = yellow,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun DifficultyView(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    difficultyItems: List<DifficultyItem>,
) {

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {

        HorizontalPager(
            modifier = modifier,
            state = pagerState,
        ) { page ->

            DifficultyItem(
                modifier = Modifier.fillMaxSize(),
                difficulty = difficultyItems[page],
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PagerButtons(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onLeftClicked: () -> Unit,
    onRightClicked: () -> Unit,
) {

    val enableLeft = remember { derivedStateOf { pagerState.currentPage > 0 } }
    val enableRight = remember {
        derivedStateOf { pagerState.currentPage < pagerState.pageCount - 1 }
    }

    val leftButtonTargetAlpha = remember { derivedStateOf { if (enableLeft.value) 1f else 0.3f } }
    val rightButtonTargetAlpha = remember { derivedStateOf { if (enableRight.value) 1f else 0.3f } }

    val leftButtonAlpha = animateFloatAsState(
        targetValue = leftButtonTargetAlpha.value,
        animationSpec = tween(durationMillis = 300),
    )
    val rightButtonAlpha = animateFloatAsState(
        targetValue = rightButtonTargetAlpha.value,
        animationSpec = tween(durationMillis = 300),
    )

    Row(
        modifier = modifier,
    ) {

        // Left icon
        Icon(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.background)
                .alpha(leftButtonAlpha.value)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape,
                )
                .clickable(enabled = enableLeft.value) { onLeftClicked() }
                .padding(8.dp)
                .align(alignment = Alignment.CenterVertically),
            imageVector = Icons.Filled.KeyboardArrowLeft,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.weight(1f))

        // Right icon
        Icon(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.background)
                .alpha(rightButtonAlpha.value)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape,
                )
                .clickable(enabled = enableRight.value) { onRightClicked() }
                .padding(8.dp)
                .align(alignment = Alignment.CenterVertically),
            imageVector = Icons.Filled.KeyboardArrowRight,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = null,
        )
    }
}



@Composable
fun GameTitle(
    modifier: Modifier = Modifier,
) {

    val title: String = "Titungan"

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        )
    }
}

@Immutable
data class DifficultyItem(
    val title: String,
    val gridMessage: String,
    val difficulty: GameDifficulty,
    val isGameInProgress: Boolean,
)

sealed class GameDifficulty {

    abstract val rows: Int
    abstract val columns: Int
    abstract val mines: Int

    internal object Easy : GameDifficulty() {
        override val rows: Int = 10
        override val columns: Int = 10
        override val mines: Int = 10
    }

    internal object Medium : GameDifficulty() {
        override val rows: Int = 16
        override val columns: Int = 16
        override val mines: Int = 35
    }
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
