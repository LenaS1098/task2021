package de.task.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.task.R

class Task(id: Int, title: String, category: String, description: String, time: Int) {
    var taskID: Int = id
    var taskTitle: String = title
    var taskCategory: String = category
    var taskDescription: String = description
    var taskTime: Int = time

}

@Composable
fun SurpriseButton() {
    val task1 = Task(
        10,
        "Liegestütze",
        "Fitness",
        "Mache 15 Liegestütze! Wenn du keine Kraft hast, mach sie Gegen eine Wand. Nächstes mal gegen einen Tisch und dann auf den Knien",
        5
        )
    val task2 = Task(
        40,
        "Staubsaugen",
        "Chores",
        "Was getan werden muss, muss getan werden",
        20
    )
    val task3 = Task(
        34,
        "Malen",
        "Relax",
        "Wow! Ein echter Göthe!",
        30
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        val context = LocalContext.current
        Button(
            onClick = {
                Toast.makeText(context,"Lass dich überraschen!",Toast.LENGTH_LONG).show()
            },
            colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Red)
        ) {
            Text("Surprise Me!")
        }
    }
}

@Composable
fun TaskCard (
    onClick: (() -> Unit)? =null
){
    Card (
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(3.dp),
        border = BorderStroke(2.dp, Color.Black),
        elevation = 12.dp,
        modifier = Modifier.fillMaxWidth()
    ){
        Column{
            val image: Painter = painterResource(id = R.drawable.defaulttask256)
            Image(
                modifier = Modifier
                    .height(225.dp),
                contentScale = ContentScale.Fit,
                painter = image,contentDescription = ""
            )
            Text(
                text = "Hallo test das ist eine Card",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun surprise(){

    var checkedState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
          val task1 = Task(
              10,
              "Liegestütze",
              "Fitness",
              "Mache 15 Liegestütze! Wenn du keine Kraft hast, mach sie Gegen eine Wand. Nächstes mal gegen einen Tisch und dann auf den Knien",
              5
              )
          val task2 = Task(
              40,
              "Staubsaugen",
              "Chores",
              "Was getan werden muss, muss getan werden",
              20
          )
          val task3 = Task(
              34,
              "Malen",
              "Relax",
              "Wow! Ein echter Göthe!",
              30
          )


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
                      Toast.makeText(context,"Lass dich überraschen!",Toast.LENGTH_LONG).show()
                      checkedState.value = true
                  },
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Red)
              ) {
                  Text("Surprise Me!")
              }
          }
      }
    if(checkedState.value) {
        Card(
            backgroundColor = Color.LightGray,
            shape = RoundedCornerShape(3.dp),
            border = BorderStroke(2.dp, Color.Black),
            elevation = 12.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row {
                    val image: Painter = painterResource(id = R.drawable.defaulttask256)
                    Image(
                        painter = image,
                        contentDescription = "",
                        modifier = Modifier.height(100.dp)
                    )
                    Text(
                        text = "Hallo test das ist eine Card",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

        }
    }
    }






