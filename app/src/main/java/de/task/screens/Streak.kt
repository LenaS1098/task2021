@file:Suppress("Annotator", "Annotator", "Annotator")

package de.task.screens

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.sigmadelta.calpose.Calpose
import be.sigmadelta.calpose.WEIGHT_7DAY_WEEK
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.model.CalposeDate
import be.sigmadelta.calpose.model.CalposeWidgets
import be.sigmadelta.calpose.widgets.DefaultDay
import be.sigmadelta.calpose.widgets.DefaultHeader
import kotlinx.coroutines.flow.MutableStateFlow

import java.time.YearMonth
import de.task.*
import de.task.DB.CompletedTask
import de.task.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*




@ExperimentalAnimationApi
@Composable
fun CalenderTab(listComletedTasks : List<CompletedTask>) {
    Column {
        CalenderTab1(listComletedTasks)
    }
}

@ExperimentalAnimationApi
@Composable
fun CalenderTab1(listComletedTasks : List<CompletedTask>){

    val listDates = getListOfDates(listComletedTasks)

    var setDates: Set<CalposeDate> = setOf()
    listDates.forEach {
        val tempDate: CalposeDate = getCalposeDate(it)
        setDates = setDates.plus(tempDate)
    }
    val monthFlow = MutableStateFlow(YearMonth.now())

    val selectionSet = MutableStateFlow(setDates)

    setDates.forEach {
        Log.e("set",it.toString())
    }
    CalendarStreak(monthFlow = monthFlow, selectionSet = selectionSet, listComletedTasks)
}

@ExperimentalAnimationApi
@Composable
fun ShowTaskOfDay(listComletedTasks: List<CompletedTask>, date: CalposeDate ){
    val liste = getTasksOfDay(listComletedTasks,date)
    LazyColumn(modifier = Modifier.padding(top = 15.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        items(liste){
                task -> CompletedTaskCard(task = task)
        }

    }
}

@ExperimentalAnimationApi
@Composable
fun CalendarStreak(
    monthFlow: MutableStateFlow<YearMonth>,
    selectionSet: MutableStateFlow<Set<CalposeDate>>, listComletedTasks: List<CompletedTask>
) {

    val selections = selectionSet.collectAsState().value
    val selectedDate = remember{ mutableStateOf(getCalposeDate(LocalDate.now())) }
    Column(modifier = Modifier
        .padding(vertical = 25.dp)
    ){
        Calpose(month = monthFlow.collectAsState().value, actions = CalposeActions(
            onClickedPreviousMonth = {
                monthFlow.value = monthFlow.value.minusMonths(1)
            },
            onClickedNextMonth = {
                monthFlow.value = monthFlow.value.plusMonths(1)
            }
        ), widgets = CalposeWidgets(
            header = { month, todayMonth, actions ->
                DefaultHeader(month = month, todayMonth = todayMonth, actions = actions)
            },
            headerDayRow = { headerDayList ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(vertical = 8.dp),
                ) {
                    headerDayList.forEach {
                        DefaultDay(
                            text = it.name.first().toString(),
                            modifier = Modifier
                                .weight(WEIGHT_7DAY_WEEK)
                                .alpha(.6f),
                            style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            },
            day = { dayDate, todayDate ->
                val isSelected = selections.contains(dayDate)
               // Log.e("dayDate", dayDate.toString())
                val onSelected = {
                    selectedDate.value = dayDate
                    }

                    /*selectionSet.value = mutableSetOf(dayDate).apply {
                        addAll(selectionSet.value)
                        Log.e("setValue",selectionSet.value.toString())
                    }*/

                val weight = if (isSelected) 1f else WEIGHT_7DAY_WEEK
                val bgColor = if (isSelected) MaterialTheme.colors.primaryVariant else Color.Transparent

                val widget: @Composable () -> Unit = {
                    DefaultDay(
                        text = dayDate.day.toString(),
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(weight)
                            .fillMaxWidth(),
                        style = TextStyle(
                            color = when {
                                isSelected -> Color.White
                                else -> Color.Black
                            },
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }

                Column(
                    modifier = Modifier.weight(WEIGHT_7DAY_WEEK),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Crossfade(targetState = bgColor) {
                        Box(
                            modifier = Modifier.size(28.dp).clip(CircleShape)
                                .clickable(onClick = onSelected)
                                .background(it)
                        ) {
                            widget()
                        }
                    }

                }
            },
            priorMonthDay = { dayDate ->
                DefaultDay(
                    text = dayDate.day.toString(),
                    style = TextStyle(color = Color.DarkGray),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .weight(WEIGHT_7DAY_WEEK)
                )
            },
            headerContainer = { header ->
                Card {
                    header()
                }
            },

            )
        )
    }
    if(selections.contains(selectedDate.value)){
        ShowTaskOfDay(listComletedTasks = listComletedTasks, date = selectedDate.value)
    }else{
        Column(modifier = Modifier.padding(top = 15.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(
                text = "An diesem Tag wurde keine Aufgabe erledigt :(",
                modifier = Modifier.padding(start = 10.dp),
                textAlign = TextAlign.Center
            )

        }
    }

}
@ExperimentalAnimationApi
@Composable
fun CompletedTaskCard(task: CompletedTask){
    val taskId = task.categoryId
    val pId : Int
    when(taskId){
        1 -> pId = R.drawable.running
        2 -> pId = R.drawable.flower
        3 -> pId = R.drawable.relax
        4 -> pId = R.drawable.staubsauger
        5 -> pId = R.drawable.cooking2
        else -> pId = R.drawable.task
    }
    val color: Color = when(taskId){
        3 -> Color(0xFF74B49B) //green
        5 -> Color(0xFFA4C5C6) //blue
        2 -> Color(0xFF856C8B)  //purple
        4 -> Color(0xFFF6D186) //yellow
        1 -> Color(0xFFE97A7A)     //red
        else -> MaterialTheme.colors.primary
    }

    val clicked = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
            .clickable {
            },
        shape = RoundedCornerShape(15.dp),
        backgroundColor = color
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    clicked.value = !clicked.value
                }
        ) {
            Image(

                bitmap = ImageBitmap.imageResource(id = pId),
                contentDescription = "TaskImage",
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .size(100.dp)
                    .padding(start = 10.dp)
            )
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = task.name, fontSize = 24.sp, modifier = Modifier.padding(top = 10.dp), color = MaterialTheme.colors.onPrimary)
                Text(text = "Dauer:  "+ task.duration + " Minuten", modifier = Modifier.padding(top = 6.dp), color = MaterialTheme.colors.onPrimary)
                Text(text = "Kategorie:  ${task.categoryId}", modifier = Modifier.padding(top = 4.dp), color = MaterialTheme.colors.onPrimary)
                AnimatedVisibility(clicked.value) {
                    Text(
                        text = task.description,
                        modifier = Modifier.padding(top = 10.dp),
                        fontStyle = FontStyle.Italic
                    )
                }
            }

        }
    }
}

//gibt die Daten aller CompletedTask in einer Liste wieder
fun getListOfDates(listComletedTasks : List<CompletedTask>): MutableList<LocalDate>{
    val listDates: MutableList<LocalDate> = mutableListOf()
    listComletedTasks.forEach {
        val localDate = LocalDate.parse(it.date, DateTimeFormatter.ISO_DATE)
        //val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        Log.e("DateFormat",localDate.toString())
        listDates.add(localDate)
    }
    return listDates
}

//LocalDate --> CalposeDate
fun getCalposeDate(date: LocalDate): CalposeDate {
    return CalposeDate(date.dayOfMonth, date.dayOfWeek, getYearMonth(date))
}
fun getYearMonth(date: LocalDate) : YearMonth{
    return YearMonth.of(date.year, date.month)
}

fun calposeDateToString(date: CalposeDate): String{
    val year = date.month.toString()
    val day = date.day.toString()
    return "$year-$day"
}

fun getTasksOfDay(listComletedTasks: List<CompletedTask>, date: CalposeDate ) : MutableList<CompletedTask>{
    val list = mutableListOf<CompletedTask>()
    listComletedTasks.forEach {
        if(it.date.equals(calposeDateToString(date))){
            list.add(it)
        }
    }
    return list
}
