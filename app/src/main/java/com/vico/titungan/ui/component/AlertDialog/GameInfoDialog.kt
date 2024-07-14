package com.vico.titungan.ui.component.AlertDialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun GameInfoDialog(
    onDialogClick : () -> Unit
) {
    AlertDialog(
        onDismissRequest = {  },
        title = {
            Text(text = "Game Info")
        },
        text = {
            Text(text = "Ini adalah informasi tentang game.")
        },
        confirmButton = {
            TextButton(onClick = onDialogClick) {
                Text("OK")
            }
        }
    )
}