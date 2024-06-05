package com.vico.titungan.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp

@Composable
fun AnimatingText(
    modifier: Modifier = Modifier,
    time: String,
) {

    AnimatedContent(
        modifier = modifier,
        transitionSpec = {
            slideIn(
                initialOffset = { fullSize -> IntOffset(x = 0, y = -fullSize.height) },
                animationSpec = tween(durationMillis = 300),
            ) togetherWith slideOut(
                targetOffset = { fullSize -> IntOffset(x = 0, y = fullSize.height) },
                animationSpec = tween(durationMillis = 300)
            )
        },
        targetState = time, label = "",
    ) { targetState ->

        Text(
            text = targetState,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
        )
    }
}