package de.task.screens

<<<<<<<<< Temporary merge branch 1
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.Calpose
import be.sigmadelta.calpose.CalposeHeader
import be.sigmadelta.calpose.WEIGHT_7DAY_WEEK
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.model.CalposeDate
import be.sigmadelta.calpose.model.CalposeWidgets
import be.sigmadelta.calpose.widgets.DefaultDay
import be.sigmadelta.calpose.widgets.DefaultHeader
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.DayOfWeek

import java.time.YearMonth
import de.task.*



@Composable
fun streak() {
    val monthFlow = MutableStateFlow(YearMonth.now())

    val selectionSet = MutableStateFlow(setOf<CalposeDate>())
    selectionSet.value = getStreakDateS()

    CalendarStreak(monthFlow = monthFlow, selectionSet = selectionSet)

}

fun getStreakDateS(): Set<CalposeDate>{
    val date1  = CalposeDate(10,DayOfWeek.WEDNESDAY, YearMonth.now())
    val date2  = CalposeDate(9,DayOfWeek.TUESDAY, YearMonth.now())
    val date3  = CalposeDate(8,DayOfWeek.MONDAY, YearMonth.now())
    val date4 = CalposeDate(20,DayOfWeek.SATURDAY, YearMonth.now().minusMonths(1))

    val set :Set<CalposeDate> = setOf(date1,date2,date3,date4)
    return set
}

@Composable
fun CalendarStreak(
    monthFlow: MutableStateFlow<YearMonth>,
    selectionSet: MutableStateFlow<Set<CalposeDate>>
) {

    val selections = selectionSet.collectAsState().value
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
                    selectionSet.value = mutableSetOf(dayDate).apply {
                        addAll(selectionSet.value)
                        Log.e("setValue",selectionSet.value.toString())
                    }
                }
                val weight = if (isSelected) 1f else WEIGHT_7DAY_WEEK
                val bgColor = if (isSelected) Color.Red else Color.Transparent

                val widget: @Composable () -> Unit = {
                    DefaultDay(
                        text = dayDate.day.toString(),
                        modifier = Modifier.padding(4.dp).weight(weight).fillMaxWidth(),
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
}

