package de.task.screens


import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.task.DB.CompletedTask
import de.task.DB.Task
import de.task.DB.TaskViewModel
import de.task.R
import de.task.ui.theme.LightGreen
import java.time.LocalDate
import java.time.LocalTime


//To Do: Beim aktuellen Tag --> Knopf mit Task hinzufügen
//To Do: Nach Uhrzeiten sortieren





@ExperimentalAnimationApi
@Composable
fun TaskCard(task: Task, currentList: MutableState<List<Task>>, taskViewModel: TaskViewModel) {
    val pId: Int = when (task.categoryId) {
        1 -> R.drawable.running
        2 -> R.drawable.flower
        3 -> R.drawable.relax
        4 -> R.drawable.staubsauger
        5 -> R.drawable.cooking2
        else -> R.drawable.task
    }
    val color: Color = when(task.categoryId){
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
            .padding(bottom = 20.dp)
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
                    .weight(2f)
            )
            Column(modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(3f))
            {
                Text(text = task.name, fontSize = 24.sp, modifier = Modifier.padding(top = 10.dp), color = MaterialTheme.colors.onPrimary)
                Text(
                    text = "Dauer:  " + task.duration + " Minuten",
                    modifier = Modifier.padding(top = 6.dp),
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = "Kategorie:  ${task.categoryId}",
                    modifier = Modifier.padding(top = 4.dp, end = 4.dp),
                    color = MaterialTheme.colors.onPrimary
                )
                AnimatedVisibility(visible = clicked.value) {
                    Text(
                        text = task.description,
                        modifier = Modifier.padding(top = 10.dp, end = 4.dp),
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colors.onPrimary
                    )
                }}
            FloatingActionButton(
                onClick = {
                    val newList = mutableListOf<Task>()
                    currentList.value.forEach { t ->
                        if (t.id != task.id) {
                            newList.add(t)
                        }
                    }
                    currentList.value = newList
                    taskViewModel.insert(
                        CompletedTask(
                            0,
                            task.name,
                            task.description,
                            task.duration,
                            task.categoryId,
                            task.pictureId,
                            LocalDate.now().toString(),
                            LocalTime.now().toString()
                        )
                    )
                },
                backgroundColor = Color(0xFFD4EBD0),
                content = {
                    Icon(painter = painterResource(id = R.drawable.ic_accept),contentDescription = "Accept Icon")
                }, modifier = Modifier.padding(horizontal = 10.dp)
            )
            }
        }
    }



@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(bottom = 20.dp)
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
        ) {

            Text(
                text = LocalDate.now().toString(),
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )

        }

    }
}

@ExperimentalAnimationApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DummyCalendar(taskList: List<Task>, taskViewModel: TaskViewModel) {


    val currentList = remember {
        mutableStateOf(taskList)
    }

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        stickyHeader {
            Header()
        }
        items(currentList.value) { task ->
            val category: String = when(task.categoryId){
                1 -> "Sport"
                2 -> "Irgendwas"
                3 -> "Entspannung"
                4 -> "Produktiv"
                5 -> "Kochen"
                else -> "Fehler"
            }
            Text(text =category, textAlign = TextAlign.Center, style = MaterialTheme.typography.subtitle2, fontStyle = FontStyle.Italic, modifier = Modifier.padding(bottom = 3.dp))
            TaskCard(task = task, currentList, taskViewModel)
        }
    }
}
    

