package de.task.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import de.task.DB.Task
import de.task.R

@Composable
fun surpriseTaskCard(task: Task){
    val taskId = task.categoryId
    val pId : Int
    when(taskId){
        1 -> pId = R.drawable.running
        2 -> pId = R.drawable.flower
        3 -> pId = R.drawable.music
        4 -> pId = R.drawable.chores
        5 -> pId = R.drawable.cooking
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
        backgroundColor = Color.LightGray
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
            Button(
                onClick = {
                    //diese Task austauschen
                },colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Red)
            ){
                Text("Diese Aufgabe austauschen")
            }
        }
    }
}




@Composable
fun surprise(taskList: List<Task>, dailyTaskList: MutableList<Task>) {
    var acceptClicked = remember { mutableStateOf(false) }
    var surpriseCheck = remember { mutableStateOf(false) }
    var taskNo1 = 0
    var taskNo2 = 0
    var taskNo3 = 0
    var neueListe = remember { mutableListOf<Task>() }
    var surpriseShown = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom

        ) {
            val context = LocalContext.current
            Button(
                onClick = {
                    Toast.makeText(context, "Lass dich überraschen!", Toast.LENGTH_LONG).show()
                    if (surpriseCheck.value) {
                        surpriseCheck.value = false
                    } else {
                        surpriseCheck.value = true
                    }
                },
                colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Red)
            ) {
                Text("Surprise Me!")
            }
            Button(
                onClick = {
                    neueListe.removeAll(taskList)
                    neueListe.add(taskList[taskNo1])
                    neueListe.add(taskList[taskNo2])
                    neueListe.add(taskList[taskNo3])

                    dailyTaskList.removeAll(taskList)
                    dailyTaskList.addAll(neueListe)
                },
                colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Green)
            ) {
                Text("Accept")
            }
        }
    }




    if (surpriseCheck.value) {

        if (!acceptClicked.value) {
            taskNo1 = (taskList.indices).random()
            taskNo2 = (taskList.indices).random()
            taskNo3 = (taskList.indices).random()
        }
        Column {

            surpriseTaskCard(taskList[taskNo1])
            surpriseTaskCard(taskList[taskNo2])
            surpriseTaskCard(taskList[taskNo3])
        }

    }

}



