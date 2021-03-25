package de.task.screens

import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import de.task.DB.Task
import de.task.R

@Composable
fun surpriseTaskCard(task: Task, completeList:List<Task>){
    val taskId = task.categoryId
    val pId : Int
    var thisCardTask = remember {mutableStateOf(task)}

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
            .height(100.dp)
            .clickable {
            },
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.LightGray
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
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
            Column(modifier = Modifier.padding(horizontal = 8.dp).weight(3f)
            ) {
                Text(text = thisCardTask.value.name, fontSize = 24.sp, modifier = Modifier.padding(top = 10.dp))
                Text(text = "Dauer:  "+ thisCardTask.value.duration, modifier = Modifier.padding(top = 6.dp))
                Text(text = "Kategorie:  ${thisCardTask.value.categoryId}", modifier = Modifier.padding(top = 4.dp))
                if(clicked.value)
                    Text(text = task.description, modifier = Modifier.padding(top = 10.dp), fontStyle = FontStyle.Italic)
            }



            Button(onClick = {
                thisCardTask.value = completeList.get((completeList.indices).random())

            },modifier = Modifier.weight(1f)){
                Image(bitmap = ImageBitmap.imageResource(id= R.drawable.defaulttask256),
                    contentDescription = "",
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    
                    .padding(start=10.dp)
                )
            }


        }
    }
}


@Composable
fun surprise(completeList: List<Task>, dailyList: MutableList<Task>, currentList: MutableList<Task>) {
    var acceptClicked = remember { mutableStateOf(false) }
    var surpriseCheck = remember { mutableStateOf(false) }
    var taskNo1 = 0
    var taskNo2 = 0
    var taskNo3 = 0
    var neueListe = remember { mutableListOf<Task>() }
    var surpriseShown = remember { mutableStateOf(false) }
    val listofcat1 = completeList.filter { task -> task.categoryId ==1 }
    val listofcat2 = completeList.filter { task -> task.categoryId ==3 }
    val listofcat3 = completeList.filter { task -> task.categoryId ==4 }



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
                    currentList.removeAll(completeList)
                    currentList.add(listofcat1[taskNo1])
                    currentList.add(listofcat2[taskNo2])
                    currentList.add(listofcat3[taskNo3])

                    dailyList.removeAll(completeList)
                    dailyList.addAll(currentList)
                },
                colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Green)
            ) {
                Text("Accept")
            }
        }
    }




    if (surpriseCheck.value) {


        if (!acceptClicked.value) {



            taskNo1 = (listofcat1.indices).random()
            taskNo2 = (listofcat2.indices).random()
            taskNo3 = (listofcat3.indices).random()



        }
        Column {

            surpriseTaskCard(listofcat1[taskNo1],listofcat1)
            surpriseTaskCard(listofcat2[taskNo2],listofcat2)
            surpriseTaskCard(listofcat3[taskNo3],listofcat3)
        }

    }

}



