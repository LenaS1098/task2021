package de.task.screens

import android.app.slice.Slice
import android.os.Parcel
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.task.DB.CompletedTask
import de.task.DB.Task
import java.time.LocalDate


// TODO: 24.03.2021 duration in h und min

fun getHours(duration: Int) : Int{
    return duration / 60
}

fun getMinutes(duration: Int) : Int{
    val hours = getHours(duration)
    return duration - (hours * 60)
}

fun getStreak(listCompletedTasks: List<CompletedTask>): Int{
    var streak = 0
    val listDates = getListOfDates(listCompletedTasks)
    if(listDates[listDates.size-1].isEqual(LocalDate.now())){
        streak = 1
        for(i in listDates.size-1 downTo 0) {
            if (!listDates[i].isEqual(listDates[i - 1])) {
                if (listDates[i].minusDays(1).isEqual(listDates[i - 1])) {
                    streak++
                    Log.e("streak","Date: ${listDates[i].toString()} Counter: ${streak.toString()}")
                } else {
                    return streak
                }
            }
        }
    }

    return streak
}

@Composable
fun Boxes(listCompletedTasks: List<CompletedTask>) {
        Spacer(modifier = Modifier.padding(15.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .size(160.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(textAlign = TextAlign.Center, text = "Streak", fontStyle = FontStyle.Italic, fontSize = 18.sp)
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(textAlign = TextAlign.Center, text = "${getStreak(listCompletedTasks)}", style = MaterialTheme.typography.h4)
                }
            }
            Card(
                modifier = Modifier
                    .size(160.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary,

            )
            {
                var totalDuration = 0
                listCompletedTasks.forEach{
                    totalDuration += it.duration
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(textAlign = TextAlign.Center, text = "Total Duration", fontStyle = FontStyle.Italic, fontSize = 18.sp)
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Log.e("duration", totalDuration.toString())
                    Text(textAlign = TextAlign.Center, text = "${getHours(totalDuration)} h ${getMinutes(totalDuration)} min ", style = MaterialTheme.typography.h4)
                }
            }
        }
        Spacer(modifier = Modifier.padding(15.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .size(160.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(textAlign = TextAlign.Center, text = "Total Tasks", fontStyle = FontStyle.Italic, fontSize = 18.sp)
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(textAlign = TextAlign.Center, text = "${listCompletedTasks.size}", style = MaterialTheme.typography.h4)
                }



            }
            Card(
                modifier = Modifier
                    .size(160.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(textAlign = TextAlign.Center, text = "Favorite Categories", fontStyle = FontStyle.Italic, fontSize = 18.sp)
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(textAlign = TextAlign.Center, text = "PieChart", fontSize = 18.sp)
                }
            }
        }

}
