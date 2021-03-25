package de.task.screens



import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.task.DB.Task
import de.task.R
import java.time.LocalDate




//To Do: Beim aktuellen Tag --> Knopf mit Task hinzufügen
//To Do: Nach Uhrzeiten sortieren





@Composable
fun TaskCard(task: Task){
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

    val clicked = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
            .clickable {
            },
        shape = RoundedCornerShape(15.dp),
        backgroundColor = MaterialTheme.colors.primaryVariant
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
                Text(text = task.name, fontSize = 24.sp, modifier = Modifier.padding(top = 10.dp))
                Text(text = "Dauer:  "+ task.duration, modifier = Modifier.padding(top = 6.dp))
                Text(text = "Kategorie:  ${task.categoryId}", modifier = Modifier.padding(top = 4.dp))
                if(clicked.value)
                    Text(text = task.description, modifier = Modifier.padding(top = 10.dp), fontStyle = FontStyle.Italic)
            }

        }
    }
}

@Composable
fun Header(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.background)
    ) {

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)
        ) {

            Text(text = LocalDate.now().toString(), modifier = Modifier.padding(start = 10.dp, end = 10.dp) )

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DummyCalendar(taskList: List<Task>) {


    val currentList = taskList

    LazyColumn{
        stickyHeader {
            Header()
        }
        items(currentList){
        task -> TaskCard(task = task)
    }
    }
}

