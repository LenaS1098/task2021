package de.task.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.Card
import androidx.compose.material.Text
import de.task.R
import de.task.DB.Task

class hardTask(id: Int, title: String, category: String, description: String, time: Int, image: Painter) {
    var taskID: Int = id
    var taskTitle: String = title
    var taskCategory: String = category
    var taskDescription: String = description
    var taskTime: Int = time
    var taskImage: Painter = image
}


@Composable
fun surprise(taskList: List<Task>, zufuellendeListe: MutableList<Task>) {

    var surpriseCheck = remember { mutableStateOf(false) }

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
                    if(surpriseCheck.value){
                        surpriseCheck.value = false
                    }else{surpriseCheck.value=true}
                },
                colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Red)
            ) {
                Text("Surprise Me!")
            }
        }
    }

    if (surpriseCheck.value) {


        var taskNo1 = (0..taskList.size).random()
        var taskNo2 = (0..taskList.size).random()
        var taskNo3 = (0..taskList.size).random()

        Column {

            TaskCard(taskList[taskNo1])
            TaskCard(taskList[taskNo2])
            TaskCard(taskList[taskNo3])

        }
        var neueListe = mutableListOf<Task>(taskList[taskNo1],taskList[taskNo2],taskList[taskNo3])

    zufuellendeListe.removeAll(taskList)
        zufuellendeListe.addAll(neueListe)
    }
    }



