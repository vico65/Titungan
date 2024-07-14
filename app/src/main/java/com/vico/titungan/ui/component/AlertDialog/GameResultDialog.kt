package com.vico.titungan.ui.component.AlertDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vico.titungan.model.Player
import com.vico.titungan.ui.component.ShapePreview
import com.vico.titungan.ui.component.button.ColoredButton
import com.vico.titungan.ui.component.toShape
import com.vico.titungan.ui.theme.Green
import com.vico.titungan.ui.theme.Salmon

@Composable
fun GameResultDialog(
    onDismiss: () -> Unit,
    winner: Player,
    loser: Player,
    onClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TextTemplate(
                    text = "Selamat!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

        },
        text = {
            Column(
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextTemplate(
                        text = "${winner.name} telah memenangkan permainan.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    winner.shape?.toShape()?.let { ShapePreview(it, 16.dp,  Salmon ) }

                    TextTemplate(
                        text = "${winner.name} : ${winner.score}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    loser.shape?.toShape()?.let { ShapePreview(it, 16.dp,  Green ) }

                    TextTemplate(
                        text = "${loser.name} : ${loser.score}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            }
        },
        confirmButton = {
            ColoredButton(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .wrapContentSize(),
                enabled = true,
                borderColor = MaterialTheme.colorScheme.onBackground,
                text = "Menu",
                onClick = onClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    )

}

@Composable
fun TextTemplate(
    text: String,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color
        )
    )
}