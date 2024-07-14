package com.vico.titungan.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
internal fun ColoredButton(
    modifier: Modifier,
    enabled: Boolean,
    borderColor: Color,
    text: String,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        border = BorderStroke(
            width = 3.dp,
            color = borderColor,
        ),
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = Color.DarkGray.copy(alpha = 0.7f),
        ),
        shape = CircleShape
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.background,
            )
        )
    }
}