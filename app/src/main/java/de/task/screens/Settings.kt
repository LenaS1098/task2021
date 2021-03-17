package de.task.screens


import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun settingScreen() {

    //muss für jede einstellug einzeln sein; am besten extra klasse (switch? Viele Variablen?)
    val checkedState = remember { mutableStateOf(false)}

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()

    ) {
        Text("Settings", fontSize = 20.sp)

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
        ) {
            Box(modifier = Modifier
                .weight(2f)){
                Text(text = "Einstellung mit einer langen Beschreibung", fontStyle = FontStyle.Italic)

            }
            Box(modifier = Modifier
                .weight(1f)){

                Checkbox(checked = checkedState.value,modifier = Modifier.padding(start = 30.dp), onCheckedChange = { checkedState.value = it})
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
        ) {
            Box(modifier = Modifier
                .weight(2f)){
                Text(text = "Kurze Einstellung", fontStyle = FontStyle.Italic)

            }
            Box(modifier = Modifier
                .weight(1f)){

                Checkbox(checked = checkedState.value,modifier = Modifier.padding(start = 30.dp), onCheckedChange = { checkedState.value = it})
            }
        }
    }
}

