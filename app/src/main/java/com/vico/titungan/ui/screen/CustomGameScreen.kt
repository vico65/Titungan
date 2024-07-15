package com.vico.titungan.ui.screen

import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vico.titungan.R
import com.vico.titungan.ui.component.RingShape
import com.vico.titungan.ui.component.ShapePreview
import com.vico.titungan.ui.component.XShape
import com.vico.titungan.ui.component.button.ColoredButton
import com.vico.titungan.ui.screen.game.GameConstants
import com.vico.titungan.ui.theme.Green
import com.vico.titungan.ui.theme.Salmon
import com.vico.titungan.ui.theme.TitunganTheme

@Preview
@Composable
fun ClockkPreview() {

    TitunganTheme() {
        CustomGameScreen()
    }
}

@OptIn(UnstableApi::class)
@Composable
fun CustomGameScreen(
    navController : NavController = rememberNavController(),
) {
    var player1 by remember { mutableStateOf(TextFieldValue("")) }
    var player2 by remember { mutableStateOf(TextFieldValue("")) }
    var nyawa by remember { mutableStateOf(3) }
    var waktu by remember { mutableStateOf(15) }
    var tiles by remember { mutableStateOf(1) }
    var caraMenang by remember { mutableStateOf(1) }
    var defisitSkor by remember { mutableStateOf(TextFieldValue("0")) }
    var maksimumSkor by remember { mutableStateOf(TextFieldValue("0")) }
    val scrollState = rememberScrollState()

    var listOperatorFix by remember{ mutableStateOf(listOf(true, true, true, true)) }
    var playOrder by remember { mutableStateOf(1) }
    var closeTiles by remember {mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        //Page Title
        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = "Custom Game",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        )
        SpacerBetweenRow(12.dp)

        //Player 1 and Player 2 Outlined Field
        RowInputPlayerName(player = player1, shape = XShape, onValueChange = {
            if (it.text.length <= 7) {
                player1 = it
            }
        })
        RowInputPlayerName(player = player2, shape = RingShape, onValueChange = {
            if (it.text.length <= 7) {
                player2 = it
            }
        })

        //Radio Button for Nyawa, Waktu, Tiles
        RadioButtonTemplate(
            elementName = "Nyawa",
            list = GameConstants.listNyawa,
            element = nyawa
        ) { nyawa = it }

        RadioButtonTemplate(
            elementName = "Waktu",
            list = GameConstants.listWaktu,
            element = waktu
        ) { waktu = it }

        RadioButtonTemplateTiles(
            elementName = "Tiles",
            list = GameConstants.tiles,
            elementPosition = tiles
        ) { tiles = it }

        //Checkbox for operator
        CheckBoxTemplateOperator(
            list = GameConstants.listOperators,
            listOperatorFix = listOperatorFix
        ) { i, b ->
            listOperatorFix = listOperatorFix.toMutableList().apply { this[i] = b }
        }

        //Radio Button for Cara Menang
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
        ) {
            LabelText(text = "Cara Menang")
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = caraMenang == 1, onClick = { caraMenang = 1 })
                    Text("Sampai tile habis")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RadioButton(selected = caraMenang == 2, onClick = { caraMenang = 2 })
                    Text("Defisit skor")
                    AnimatedVisibility(
                        visible = caraMenang == 2,
                        enter = scaleIn(),
                        exit = scaleOut(),
                        content = {
                            TextFieldHowToWin(
                                value = defisitSkor,
                                onValueChange = { defisitSkor = it },
                                isSelected = caraMenang == 2
                            )
                        }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RadioButton(selected = caraMenang == 3, onClick = { caraMenang = 3 })
                    Text("Maksimum skor")
                    AnimatedVisibility(
                        visible = caraMenang == 3,
                        enter = scaleIn(),
                        exit = scaleOut(),
                        content = {
                            TextFieldHowToWin(
                                value = maksimumSkor,
                                onValueChange = {
                                                if(it.text.toInt() < (tiles + 2) * (tiles + 2) && it.text.toInt() % 2 != 0) {
                                                    maksimumSkor = it
                                                }
                                },
                                isSelected = caraMenang == 3
                            )
                        }
                    )
                }
            }
        }

        //Radio Button for Play Order
        RadioButtonTemplatePlayOrder(
            elementName = "Starter",
            list = GameConstants.listGameFirstPlayOption,
            element = playOrder
        ) { playOrder = it }

        //checkbox for close tiles
        checkBoxForCloseTiles(closeTiles = closeTiles) {
            closeTiles = it
        }

        //button start game
        ColoredButton(
            modifier = Modifier
                .padding(start = 16.dp)
                .wrapContentSize(),
            text = "Mulai",
            onClick = {
                var player1Name = if(player1.text != "") player1.text else "Player 1"
                var player2Name = if(player2.text != "") player2.text else "Player 2"
                var listOperators = listOperatorsSend(listOperatorFix)

//                navController.navigate("game/Player 1/Player 2/$nyawa/$waktu/${tiles + 2}/$caraMenang/0/0/+,-,x,%2F/3/true")

                navController.navigate("game/$player1Name/$player2Name/$nyawa/$waktu/${tiles + 2}/$caraMenang/${defisitSkor.text.toInt()}/${maksimumSkor.text.toInt()}/$listOperators/$playOrder/$closeTiles")
            },
            enabled = true,
            borderColor = MaterialTheme.colorScheme.onBackground,
        )
    }
}

internal fun listOperatorsSend(
    list: List<Boolean>
) : String{
    var newList = ""

    list.forEachIndexed{
        index, b -> if(b && index != 3) newList += GameConstants.listOperators[index]
    }

    newList = newList.toList().joinToString(",")

    if(list[3]) {
        if(list.count {it} == 1) newList += "%2F"
        else newList += ",%2F"
    }

    return newList
}

@Composable
internal fun SpacerBetweenRow(height : Dp = 8.dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
internal fun RowInputPlayerName(
    player: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    shape: Shape
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = player,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(2f)
                .height(50.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(100),
                ),
            placeholder = {
                Text(
                    stringResource(id = if(shape == XShape) R.string.first_player_default_name else R.string.second_player_default_name),
                    color = Color.Gray)
            },
            textStyle = TextStyle(fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.SemiBold),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        ShapePreview(shape, 30.dp, if(shape == XShape) Green else Salmon )
    }

    SpacerBetweenRow()
}

@Composable
internal fun LabelText(text : String) {
    Text(text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
}

@Composable
internal fun RadioButtonTemplate(
    elementName: String,
    list : List<Int>,
    element : Int,
    onClick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        LabelText(elementName)
        list.forEach {
            RadioButton(selected = element == it, onClick = { onClick(it) })
            Text(it.toString(), Modifier.padding(end = 12.dp))
        }
    }

    SpacerBetweenRow(4.dp)
}

@Composable
internal fun RadioButtonTemplateTiles(
    elementName: String,
    list : List<String>,
    elementPosition : Int,
    onClick: (Int) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
    ) {
        LabelText(text = elementName)
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                for (i in 0..list.size) {
                    RadioButton(selected = elementPosition == i + 1, onClick = { onClick(i + 1) })
                    Text(list[i], Modifier.padding(end = 12.dp))

                    if(i == 2) break
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                for (i in 3 until list.size) {
                    RadioButton(selected = elementPosition == i + 1, onClick = { onClick(i + 1) })
                    Text(list[i], Modifier.padding(end = 12.dp))
                }
            }

        }
    }

    SpacerBetweenRow()
}

@Composable
internal fun CheckBoxTemplateOperator(
    list : List<String>,
    listOperatorFix : List<Boolean>,
    onCheckedChange : (Int, Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
    ) {
        LabelText(text = "Operator")
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            list.forEachIndexed { i, operator ->
                Checkbox(
                    checked = listOperatorFix[i],
                    onCheckedChange = { onCheckedChange(i, it) },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = MaterialTheme.colorScheme.background
                    )
                )
                Text(operator, Modifier.padding(end = 6.dp), fontSize = 20.sp)
            }
        }
        SpacerBetweenRow()
    }

}

@Composable
internal fun TextFieldHowToWin(
    value : TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isSelected : Boolean
) {
    OutlinedTextField(
        enabled = isSelected,
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .height(45.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(100),
            ),
        textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.SemiBold),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            disabledBorderColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
internal fun RadioButtonTemplatePlayOrder(
    elementName: String,
    list : List<String>,
    element : Int,
    onClick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
    ) {
        LabelText(text = elementName)
        list.forEach {
            RadioButton(selected = element == list.indexOf(it) + 1, onClick = { onClick(list.indexOf(it) + 1) })
            Text(it, Modifier.padding(end = 4.dp))
        }
    }

    SpacerBetweenRow(12.dp)
}

@Composable
internal fun checkBoxForCloseTiles(
    closeTiles : Boolean,
    onCheckedChange : (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
    ) {
        LabelText(text = "Tutup Tiles")
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = closeTiles,
                onCheckedChange = { onCheckedChange(it) },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = MaterialTheme.colorScheme.background
                )
            )
            Text("Tutup")

        }
        SpacerBetweenRow()
    }
}