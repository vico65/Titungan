package com.vico.titungan.ui.component.AlertDialog

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
fun DialogExitFromGame(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Konfirmasi Keluar",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        },
        text = {
            Text(
                text = "Apakah Anda yakin ingin keluar? Game akan selesai.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        confirmButton = {
            ColoredButton(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .wrapContentSize(),
                enabled = true,
                borderColor = MaterialTheme.colorScheme.onBackground,
                text = "Ya",
                onClick = onConfirm
            )
        },
        dismissButton = {
            ColoredButton(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .wrapContentSize(),
                enabled = true,
                borderColor = MaterialTheme.colorScheme.onBackground,
                text = "Tidak",
                containerColor = MaterialTheme.colorScheme.error,
                onClick = onDismiss
            )
        }
    )
}