package com.vico.titungan.ui.screen.game

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuSample() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Option 1") }
    val options = listOf("Option 1", "Option 2", "Option 3")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {  },
            label = { Text("Number 1") },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = "",
            onValueChange = {  },
            label = { Text("Number 2") },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )


        Box(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                modifier = Modifier
                    .clickable { expanded = true }
                    .width(64.dp),
                readOnly = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        Modifier.clickable { expanded = true }
                    )
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { option ->
                    DropdownMenuItem(onClick = {
                        selectedOption = option
                        expanded = false
                    }, text = {Text(text = option)})
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorRow() {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var selectedOperator by remember { mutableStateOf("+") }
    val operators = listOf("+", "-", "x", "/")

//    Log.i("Hasil", "Angka 1 adalah ${gameState.numberInput1.value}, angka 2 adalah ${gameState.numberInput2.value}, dan hasilnya adalah ${hasil}")
}
@Preview(showBackground = true)
@Composable
fun DropdownMenuSamplePreview() {
    CalculatorRow()
}