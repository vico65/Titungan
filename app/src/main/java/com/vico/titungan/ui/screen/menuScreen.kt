package com.vico.titungan.ui.screen

import android.annotation.SuppressLint
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vico.titungan.R
import com.vico.titungan.ui.component.button.RoundedButton
import com.vico.titungan.ui.navigation.Nav
import com.vico.titungan.ui.theme.yellow
import kotlinx.coroutines.launch

//@Preview(showBackground = true)
//@Composable
//fun HelloJetpackComposeAppPreview() {
//    TitunganTheme() {
//        MenuScreen(navController.navigate(Nav.Routes.settings))
//    }
//}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuScreen(
   navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val gameCategory: MutableState<List<GameCategoryItem>> = mutableStateOf(listOf(
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
                gameCategoryItem = gameCategory.value,
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
            onStartClicked = {
                //                val item = difficulties.value[pagerState.currentPage].difficulty
                //
                val array = arrayOf("add", "sub", "mul", "div")
                val result = array.joinToString(separator = ",")
                navController.navigate("game/Vico/Ridho/5/15/4/1/0/0/+,-/1")
            },
            onSettingsClicked = {  },
        )
    }


}

@Composable
fun GameButtons(
    modifier: Modifier = Modifier,
    onStartClicked: () -> Unit,
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