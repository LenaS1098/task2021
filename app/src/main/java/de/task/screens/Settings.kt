package de.task.screens

import android.content.res.Resources
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import de.task.DB.TaskViewModel
import de.task.ThemeState

@Composable
fun SettingTab(taskViewModel: TaskViewModel){
    val darkModus = rememberSaveable{ mutableStateOf(false)}

    Spacer(modifier = Modifier.padding(15.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(2f)
                .padding(start = 20.dp)
        ) {
            Text(
                text = "Dark Mode",
                fontStyle = FontStyle.Italic
            )

        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 15.dp)
        ) {

            Switch(
                checked = darkModus.value,
                modifier = Modifier.padding(start = 30.dp),
                onCheckedChange = { darkModus.value = it
                    taskViewModel.isDark.value = darkModus.value
                }
            )
        }
    }
}