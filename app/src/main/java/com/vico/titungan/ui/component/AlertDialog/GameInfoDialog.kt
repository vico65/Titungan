package com.vico.titungan.ui.component.AlertDialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vico.titungan.ui.component.button.ColoredButton

@Composable
fun GameInfoDialog(
    onConfirm: () -> Unit,
    detail: List<String>? = null
) {
    AlertDialog(
        onDismissRequest = {  },
        title = {
            Text(
                text = "Detail game",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        },
        text = {
            Column {
                detail?.forEach {
                    Row {
                        Text(
                            text = "- $it",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
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
                text = "Ok",
                onClick = onConfirm
            )
        }
    )
}