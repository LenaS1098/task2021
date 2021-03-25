package de.task.screens


import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp


import de.task.DB.TaskViewModel
import de.task.R
import de.task.ThemeState
import de.task.setReminder


@Composable
fun SettingTab(taskViewModel: TaskViewModel, context : Context) {
    val darkModus = rememberSaveable { mutableStateOf(false) }
    val reminder = rememberSaveable { mutableStateOf(false) }

    Spacer(modifier = Modifier.padding(10.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(top = 20.dp)
                ,
            shape = RoundedCornerShape(15.dp),
            backgroundColor = Color.LightGray
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_darkmode),
                    "icon",
                    modifier = Modifier.size(40.dp)
                )
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
                        onCheckedChange = {
                            darkModus.value = it
                            taskViewModel.isDark.value = darkModus.value
                        }
                    )
                }
            }
        }
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(top = 15.dp),
            shape = RoundedCornerShape(15.dp),
            backgroundColor = Color.LightGray
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification_off),
                    "icon",
                    modifier = Modifier.size(40.dp)
                )
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 20.dp)
                ) {
                    Text(
                        text = "Notifications",
                        fontStyle = FontStyle.Italic
                    )

                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 15.dp)
                ) {

                    Checkbox(
                        checked = reminder.value,
                        modifier = Modifier.padding(start = 30.dp),
                        onCheckedChange = {
                            setReminder(context, "Erinnerung für Tasks")
                            reminder.value = it
                            taskViewModel.reminder.value = reminder.value
                        }
                    )
                }
            }
        }

    }
}


