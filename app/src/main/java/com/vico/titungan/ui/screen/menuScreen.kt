package com.vico.titungan.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vico.titungan.R
import com.vico.titungan.domain.navigation.Screen
import com.vico.titungan.ui.component.AppButton
import com.vico.titungan.ui.component.HighlightedRoundedButton
import com.vico.titungan.ui.component.RoundedButton
import com.vico.titungan.ui.component.displayLarge
import com.vico.titungan.ui.theme.TitunganTheme
import com.vico.titungan.ui.theme.border2dp
import com.vico.titungan.ui.theme.padding16dp
import com.vico.titungan.ui.theme.width248dp
import com.vico.titungan.ui.theme.padding64dp
import com.vico.titungan.ui.theme.yellow
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun HelloJetpackComposeAppPreview() {
    var navController: NavHostController = rememberNavController()

    TitunganTheme() {
        MenuScreen(navController)
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuScreen(navController: NavHostController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(padding16dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        val context = LocalContext.current
//        displayLarge(text = stringResource(id = R.string.app_name))
//        AppButton(
//            modifier = Modifier
//                .width(width248dp)
//                .padding(top = padding64dp),
//            text = stringResource(R.string.new_game)
//        ) {  } //context.launchActivity<GameActivity>()
//        AppButton(
//            modifier = Modifier.width(width248dp),
//            text = stringResource(id = R.string.high_score)
//        ) {
//            navController.navigate(Screen.HighScores.route)
//        }
//        AppButton(modifier = Modifier.width(width248dp), text = stringResource(R.string.settings)) {
//            navController.navigate(Screen.Settings.route)
//        }
//        AppButton(modifier = Modifier.width(width248dp), text = stringResource(R.string.about)) {
//            navController.navigate(Screen.About.route)
//        }
//    }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val canResume = remember { mutableStateOf(false) }
    val _difficultyItems: MutableState<List<GameCategoryItem>> = mutableStateOf(listOf(
        GameCategoryItem(
            title = "Quick Game",
            isGameInProgress = false,
            message = "7 x 7"
        ),
        GameCategoryItem(
            title = "Custom",
            isGameInProgress = false,
            message = "Atur sendiri peraturan game anda"
        )
    ))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(top = 20.dp),
    ) {
        GameTitle(
            modifier = Modifier
                .fillMaxHeight(fraction = 0.2f)
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.5f)
        ) {

            GameCategoryView(
                modifier = Modifier.fillMaxSize(),
                pagerState = pagerState,
                gameCategoryItem = _difficultyItems.value,
            )

            PagerButtons(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                pagerState = pagerState,
                onLeftClicked = {
                    coroutineScope.launch {
                        val currentPage = pagerState.currentPage
                        if (currentPage > 0)
                            pagerState.animateScrollToPage(page = currentPage - 1)
                    }
                                },
                onRightClicked = {
                    coroutineScope.launch {
                        val currentPage = pagerState.currentPage
                        if (currentPage < pagerState.pageCount - 1)
                            pagerState.animateScrollToPage(page = currentPage + 1)
                    }
                                 },
            )
        }

        GameButtons(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            canResume = canResume,
            onStartClicked = {
                //                val item = difficulties.value[pagerState.currentPage].difficulty
                //
            },
            onResumeClicked = {
                //                val item = difficulties.value[pagerState.currentPage].difficulty
                //                onResumeClicked(item)
            },
            onSettingsClicked = {  },
        )
    }


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
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.weight(1f))

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
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
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

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun GameCategoryView(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    gameCategoryItem: List<GameCategoryItem>,
) {

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {

        HorizontalPager(
            modifier = modifier,
            state = pagerState,
        ) { page ->

            GameCategoryItem(
                modifier = Modifier.fillMaxSize(),
                category = gameCategoryItem[page],
            )
        }
    }
}

@Composable
internal fun GameCategoryItem(
    modifier: Modifier = Modifier,
    category: GameCategoryItem,
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
                text = category.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium
                ),
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                modifier = Modifier
                    .wrapContentSize(),
                text = category.message,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                ),
            )

            Row {

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .wrapContentSize()
                        .align(alignment = Alignment.CenterVertically),
                    text = "Game cepat, 5 skor maks",
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
                        .size(36.dp)
                        .background(color = Color.Transparent),
                    painter = painterResource(id = R.drawable.bolt),
                    tint = yellow,
                    contentDescription = null,
                )
            }
        }
    }
}

@Immutable
data class GameCategoryItem(
    val title: String,
    val isGameInProgress: Boolean,
    val message: String
)

//sealed class GameDifficulty {
//
//    abstract val rows: Int
//    abstract val columns: Int
//    abstract val mines: Int
//
//    internal object QuickGame : GameDifficulty() {
//        override val rows: Int = 10
//        override val columns: Int = 10
//        override val mines: Int = 10
//    }
//
//    internal object Medium : GameDifficulty() {
//        override val rows: Int = 16
//        override val columns: Int = 16
//        override val mines: Int = 35
//    }
//}