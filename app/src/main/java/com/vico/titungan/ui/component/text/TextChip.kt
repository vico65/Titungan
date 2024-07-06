package com.vico.titungan.ui.component.text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview


@Composable
internal fun TextChip(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    strokeColor: Color = MaterialTheme.colorScheme.onBackground,
) {

    Text(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(color = backgroundColor)
            .border(
                width = 2.dp,
                color = strokeColor,
                shape = RoundedCornerShape(100),
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        ),
    )
}

@Preview
@Composable
private fun Preview() {

    GameIdleTopBar(
        modifier = Modifier.wrapContentSize(),
    )


}

@Composable
internal fun GameIdleTopBar(
    modifier: Modifier = Modifier,
) {

    TextChip(
        modifier = modifier,
        text = "Tap a cell to start",
    )
}