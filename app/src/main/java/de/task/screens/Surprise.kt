package de.task.screens

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import de.task.DB.Task
import de.task.R
import de.task.ui.theme.*

@Composable
fun surpriseTaskCard(
    task: Task,
    completeList: MutableList<Task>,
    taskNo: MutableState<Int>,
    change: MutableState<Boolean>
){
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
                clicked.value = !clicked.value
            },
        shape = RoundedCornerShape(15.dp),
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
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
                .weight(3f)
            ) {
                Text(text = thisCardTask.value.name, fontSize = 24.sp, modifier = Modifier.padding(top = 10.dp))
                Text(text = "Dauer:  "+ thisCardTask.value.duration + " Minuten", modifier = Modifier.padding(top = 6.dp))
                Text(text = "Kategorie:  ${thisCardTask.value.categoryId}", modifier = Modifier.padding(top = 4.dp))
                if(clicked.value)
                    Text(text = task.description, modifier = Modifier.padding(top = 10.dp), fontStyle = FontStyle.Italic)
            }

            IconButton(
                onClick = {
                    thisCardTask.value = completeList.get(taskNo.value % completeList.size)
                    taskNo.value++
                    Log.e("random",taskNo.value.toString()+ " " + thisCardTask.value.name)
                    change.value = true
                },modifier = Modifier.weight(1f)
            ){
                Image(painter = painterResource(id = R.drawable.ic_baseline_refresh_24),contentScale = ContentScale.FillWidth,
                    contentDescription = "")
            }
        }
    }
}


@Composable
fun surprise(completeList: List<Task>, dailyList: MutableList<Task>, currentList: MutableList<Task>) {


    val listofcat1: MutableList<Task> = completeList.filter { task -> task.categoryId ==1 } as MutableList<Task>
    val listofcat2:  MutableList<Task> = completeList.filter { task -> task.categoryId ==3 } as MutableList<Task>
    val listofcat3:  MutableList<Task> = completeList.filter { task -> task.categoryId ==4 } as MutableList<Task>

    val randomStart1 = (listofcat1.indices).random()
    val randomStart2 = (listofcat2.indices).random()
    val randomStart3 = (listofcat3.indices).random()

    var lock = remember { mutableStateOf(false) }

    var change1 = remember { mutableStateOf(false) }
    var change2 = remember { mutableStateOf(false) }
    var change3 = remember { mutableStateOf(false) }



    var acceptClicked = remember { mutableStateOf(false) }
    var surpriseCheck = remember { mutableStateOf(false) }
    var taskNo1  = remember {mutableStateOf(randomStart1)}
    var taskNo2 = remember {mutableStateOf(randomStart2)}
    var taskNo3 = remember {mutableStateOf(randomStart3)}

    var showAccept = remember {mutableStateOf(false)}


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
                    if (surpriseCheck.value) {
                        surpriseCheck.value = false
                    } else {
                        surpriseCheck.value = true
                    }
                    showAccept.value = true
                },
                colors = ButtonDefaults.textButtonColors(backgroundColor = MediumYellow)
            ) {
                Text("Surprise Me!")
            }
            if(showAccept.value) {
                Button(
                    onClick = {
                        Toast.makeText(context, "Akzeptiert!", Toast.LENGTH_LONG).show()
                        currentList.removeAll(completeList)
                        if(change1.value){taskNo1.value--}
                        if(change2.value){taskNo2.value--}
                        if(change3.value){taskNo3.value--}

                        Log.e("accept",taskNo1.value.toString() + taskNo2.value.toString() + taskNo3.value.toString())
                        currentList.add(listofcat1[taskNo1.value % listofcat1.size])
                        Log.e("list1", currentList.get(0).name)
                        currentList.add(listofcat2[taskNo2.value % listofcat2.size])
                        Log.e("list2", currentList.get(1).name)

                        currentList.add(listofcat3[taskNo3.value % listofcat3.size])
                        Log.e("list3", currentList.get(2).name)


                        dailyList.removeAll(completeList)
                        dailyList.addAll(currentList)
                        dailyList.forEach{t ->Log.e("daily",t.name )}
                    },
                    colors = ButtonDefaults.textButtonColors(backgroundColor = MediumPurple)
                ) {
                    Text("Accept")
                }
            }
        }
    }

    if (!surpriseCheck.value){
        lock.value = false
    }

    if (surpriseCheck.value) {
        if (!acceptClicked.value && !lock.value ) {
            taskNo1.value += 1
            taskNo2.value += 1
            taskNo3.value += 1
            Log.e("zufall",taskNo1.value.toString() + taskNo2.value.toString() + taskNo3.value.toString())
            lock.value = true
        }
        Column {
            surpriseTaskCard(listofcat1[taskNo1.value % listofcat1.size],listofcat1,taskNo1,change1)
            surpriseTaskCard(listofcat2[taskNo2.value % listofcat2.size],listofcat2,taskNo2,change2)
            surpriseTaskCard(listofcat3[taskNo3.value % listofcat3.size],listofcat3,taskNo3,change3)
        }
    }
}




