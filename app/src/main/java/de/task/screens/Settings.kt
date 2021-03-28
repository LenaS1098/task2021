package de.task.screens


import android.app.PendingIntent
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.timePicker


import de.task.DB.TaskViewModel
import de.task.R
import de.task.cancelReminder
import de.task.setReminder
import java.sql.Time
import java.text.NumberFormat
import java.time.LocalTime


@Composable
fun SettingTab(taskViewModel: TaskViewModel, context: Context) {
    val darkModus = rememberSaveable { mutableStateOf(false) }
    val reminder = rememberSaveable { mutableStateOf(false) }
    val timeState = rememberSaveable { mutableStateOf(taskViewModel.reminderTime.value) }
    val pendingIntent: MutableState<PendingIntent?> = rememberSaveable { mutableStateOf(null) }
    val noReminderPainter: Painter = painterResource(id = R.drawable.ic_notification_off)
    val reminderPainter: Painter = painterResource(id = R.drawable.ic_notification_on)
    val notificationIcon = remember { mutableStateOf(noReminderPainter) }

    val timeDialog = MaterialDialog(context).apply {
        timePicker(show24HoursView = true) { dialog, datetime ->
            val tes = datetime.time
            val bla = LocalTime.of(tes.hours, tes.minutes)
            Log.e("timer", bla.toString())
            timeState.value = bla
            pendingIntent.value =
                setReminder(context, "Erinnerung für Tasks", tes.hours, tes.minutes)
        }
    }


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
                .padding(top = 20.dp),
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
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colors.onPrimary
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
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
                    painter = notificationIcon.value,
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
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colors.onPrimary
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
                        onCheckedChange = { checkState ->
                            if (checkState) {
                                notificationIcon.value = reminderPainter
                                timeDialog.show()
                            } else {
                                notificationIcon.value = noReminderPainter
                                if (pendingIntent.value != null) {
                                    cancelReminder(context, pendingIntent.value!!)
                                }

                            }
                            reminder.value = checkState
                            taskViewModel.reminder.value = reminder.value
                        }
                    )

                }
            }
        }
    }
    if (taskViewModel.reminder.value) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                shape = RoundedCornerShape(15.dp),
                backgroundColor = Color.LightGray
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }, horizontalArrangement = Arrangement.End
                ) {

                    Box(
                        modifier = Modifier
                            .weight(2f)

                    ) {
                        Text(
                            text = timeState.value.toString(),
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.clickable {
                                timeDialog.show()
                            }

                        )

                    }


                }
            }
        }
    }
}



