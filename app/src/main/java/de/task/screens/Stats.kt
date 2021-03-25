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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.task.DB.CompletedTask
import de.task.DB.Task
import java.time.LocalDate




fun getHours(duration: Int): Int {
    return duration / 60
}

fun getMinutes(duration: Int): Int {
    val hours = getHours(duration)
    return duration - (hours * 60)
}

fun getLastTaskDate(listCompletedTasks: List<CompletedTask>): LocalDate {
    val list = getListOfDates(listCompletedTasks)
    return list[list.size - 1]
}

fun getStreak(listCompletedTasks: List<CompletedTask>): Int {
    var streak = 0
    val listDates = getListOfDates(listCompletedTasks)
    val first = listDates[listDates.size - 1]
    if(listDates.size == 1){

    }
    if (first.isEqual(LocalDate.now()) || first.isEqual(LocalDate.now().minusDays(1))) {
        streak = 1
        for (i in listDates.size - 1 downTo 0) {
            if (!listDates[i].isEqual(listDates[i - 1])) {
                if (listDates[i].minusDays(1).isEqual(listDates[i - 1])) {
                    streak++
                    Log.e(
                        "streak",
                        "Date: ${listDates[i]} Counter: ${streak.toString()}"
                    )
                } else {
                    return streak
                }
            }
        }
    }
    if (first.isEqual(LocalDate.now().minusDays(1))) {
        streak--
    }
    return streak
}

fun getFavoriteCategorie(listCompletedTasks: List<CompletedTask>) :Pair<String,Int>{
    var sport = 0
    var entspannung = 0
    var haushalt = 0
    var kochen = 0
    var fehler = 0
    var category = ""
    listCompletedTasks.forEach {
        when(it.categoryId){
            1 -> {sport++}
            3 -> {entspannung++}
            4 -> {haushalt++}
            5 -> {kochen++}
            else -> {fehler ++}
        }
    }
    val max = maxOf(sport,entspannung,haushalt,kochen)
    when {
        sport == max -> {
            category = "Sport"
        }
        entspannung == max -> {
            category = "Entspannung"
        }
        haushalt == max -> {
            category = "Haushalt"
        }
        kochen == max -> {
            category = "Kochen"
        }
    }
    return Pair(category,max)
}


@Composable
fun Boxes(listCompletedTasks: List<CompletedTask>) {

    val isEmpty = remember { mutableStateOf(listCompletedTasks.isEmpty()) }
    Spacer(modifier = Modifier.padding(15.dp))
    if (isEmpty.value) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Erledige eine Task um dir deine Statistiken anzusehen!")
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
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
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Streak",
                        fontStyle = FontStyle.Italic,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "${getStreak(listCompletedTasks)}",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(top = 3.dp)

                    )
                    if(getLastTaskDate(listCompletedTasks).isEqual(LocalDate.now().minusDays(1))){
                        Text(textAlign = TextAlign.Center, text = "Go on!", modifier = Modifier.padding(top = 5.dp),fontStyle = FontStyle.Italic, style = MaterialTheme.typography.subtitle2)
                    }

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
                listCompletedTasks.forEach {
                    totalDuration += it.duration
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Total Duration",
                        fontStyle = FontStyle.Italic,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Log.e("duration", totalDuration.toString())
                    Text(
                        textAlign = TextAlign.Center,
                        text = "${getHours(totalDuration)} h ",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        text = "${getMinutes(totalDuration)} min ",
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(15.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
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
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Total Tasks",
                        fontStyle = FontStyle.Italic,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "${listCompletedTasks.size}",
                        style = MaterialTheme.typography.h4
                    )
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
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Favorite Categorie",
                        fontStyle = FontStyle.Italic,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val favorite = getFavoriteCategorie(listCompletedTasks)
                    Text(textAlign = TextAlign.Center, text = favorite.first, style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.padding(3.dp))
                    Text(textAlign = TextAlign.Center, text = favorite.second.toString(), style = MaterialTheme.typography.h5)
                }
            }
        }
    }
}
