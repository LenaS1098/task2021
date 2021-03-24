package de.task.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.task.R

class Task(id: Int, title: String, category: String, description: String, time: Int, image: Painter) {
    var taskID: Int = id
    var taskTitle: String = title
    var taskCategory: String = category
    var taskDescription: String = description
    var taskTime: Int = time
    var taskImage: Painter = image
}

@Composable
fun surprise() {

    var surpriseCheck: MutableState<Boolean> = remember { mutableStateOf(false) }
    val task1 = Task(
        10,
        "Liegestütze",
        "Fitness",
        "Mache 15 Liegestütze! Wenn du keine Kraft hast, mach sie Gegen eine Wand. Nächstes mal gegen einen Tisch und dann auf den Knien",
        5,
        painterResource(id = R.drawable.pushups256)
    )
    val task2 = Task(
        40,
        "Staubsaugen",
        "Chores",
        "Was getan werden muss, muss getan werden",
        20,
        painterResource(id = R.drawable.staubsaugen256)
    )
    val task3 = Task(
        34,
        "Malen",
        "Relax",
        "Wow! Ein echter Göthe!",
        30,
        painterResource(id = R.drawable.malen256)
    )
    val taskList = listOf(task1, task2, task3)
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {


        print(taskList[0].taskTitle)

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


        var taskNo1 = (0..2).random()
        var taskNo2 = (0..2).random()
        var taskNo3 = (0..2).random()
        Card(
            backgroundColor = Color.LightGray,
            shape = RoundedCornerShape(3.dp),
            border = BorderStroke(2.dp, Color.Black),
            elevation = 12.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row {
                    val image: Painter = taskList[taskNo1].taskImage
                    Image(
                        painter = image,
                        contentDescription = "",
                        modifier = Modifier.height(100.dp)
                    )
                    Text(
                        text = taskList[taskNo1].taskTitle,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

        }


    }

    }






