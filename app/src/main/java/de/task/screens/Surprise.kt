package de.task.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import de.task.DB.Task
import de.task.R
import de.task.ui.theme.*

@Composable
fun SurpriseTaskCard(
    task: Task,
    completeList: MutableList<Task>,
    taskNo: MutableState<Int>,
    change: MutableState<Boolean>
){
    val taskId = task.categoryId
    val thisCardTask = remember {mutableStateOf(task)}

    val pId : Int = when(taskId){
        1 -> R.drawable.running
        2 -> R.drawable.flower
        3 -> R.drawable.relax
        4 -> R.drawable.staubsauger
        5 -> R.drawable.cooking2
        else -> R.drawable.task
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
            .padding(bottom = 20.dp)
            .height(100.dp)
            .clickable {
                clicked.value = !clicked.value
            },
        shape = RoundedCornerShape(15.dp),
        backgroundColor = color
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
                    .padding(vertical = 5.dp)
                    .weight(2f)
            )
            Column(modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(3f)
            ) {
                Text(text = thisCardTask.value.name, fontSize = 24.sp, modifier = Modifier.padding(top = 10.dp), color = MaterialTheme.colors.onPrimary)
                Text(text = "Dauer:  "+ thisCardTask.value.duration + " Minuten", modifier = Modifier.padding(top = 6.dp), color = MaterialTheme.colors.onPrimary)
                Text(text = "Kategorie:  ${thisCardTask.value.categoryId}", modifier = Modifier.padding(top = 4.dp), color = MaterialTheme.colors.onPrimary)
                if(clicked.value)
                    Text(text = task.description, modifier = Modifier.padding(top = 10.dp), fontStyle = FontStyle.Italic, color = MaterialTheme.colors.onPrimary)
            }

            IconButton(
                onClick = {
                    thisCardTask.value = completeList[taskNo.value % completeList.size]
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


@ExperimentalAnimationApi
@Composable
fun Surprise(completeList: List<Task>, dailyList: MutableList<Task>, currentList: MutableList<Task>) {


    val listofcat1: MutableList<Task> = completeList.filter { task -> task.categoryId ==1 } as MutableList<Task>
    val listofcat2:  MutableList<Task> = completeList.filter { task -> task.categoryId ==3 } as MutableList<Task>
    val listofcat3:  MutableList<Task> = completeList.filter { task -> task.categoryId ==4 } as MutableList<Task>
    val listofcat4:  MutableList<Task> = completeList.filter { task -> task.categoryId ==5 } as MutableList<Task>



    val randomStart1 = (listofcat1.indices).random()
    val randomStart2 = (listofcat2.indices).random()
    val randomStart3 = (listofcat3.indices).random()
    val randomStart4 = (listofcat3.indices).random()


    val lock = remember { mutableStateOf(false) }

    val change1 = remember { mutableStateOf(false) }
    val change2 = remember { mutableStateOf(false) }
    val change3 = remember { mutableStateOf(false) }
    val change4 = remember { mutableStateOf(false) }




    val acceptClicked = remember { mutableStateOf(false) }
    val surpriseCheck = remember { mutableStateOf(false) }
    val taskNo1  = remember {mutableStateOf(randomStart1)}
    val taskNo2 = remember {mutableStateOf(randomStart2)}
    val taskNo3 = remember {mutableStateOf(randomStart3)}
    val taskNo4= remember {mutableStateOf(randomStart3)}

    val showAccept = remember {mutableStateOf(false)}


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
            Row  {
                val context = LocalContext.current
                FloatingActionButton(
                    onClick = {
                        surpriseCheck.value = !surpriseCheck.value
                        showAccept.value = true
                    },
                    backgroundColor = Color(0xFFEE99A0),
                    shape = CircleShape,
                    content = { Icon(painter = painterResource(id = R.drawable.ic_celebration), contentDescription = "Surprise Icon")
                    }
                )
                AnimatedVisibility(showAccept.value) {
                    FloatingActionButton(
                        onClick = {
                            Toast.makeText(context, "Akzeptiert!", Toast.LENGTH_LONG).show()
                            currentList.removeAll(completeList)
                            if(change1.value){taskNo1.value--}
                            if(change2.value){taskNo2.value--}
                            if(change3.value){taskNo3.value--}
                            if(change4.value){taskNo4.value--}


                            Log.e("accept",taskNo1.value.toString() + taskNo2.value.toString() + taskNo3.value.toString())
                            currentList.add(listofcat1[taskNo1.value % listofcat1.size])
                            Log.e("list1", currentList.get(0).name)
                            currentList.add(listofcat2[taskNo2.value % listofcat2.size])
                            Log.e("list2", currentList.get(1).name)

                            currentList.add(listofcat3[taskNo3.value % listofcat3.size])
                            Log.e("list3", currentList.get(2).name)

                            currentList.add(listofcat4[taskNo4.value % listofcat4.size])
                            Log.e("list4", currentList.get(3).name)


                            dailyList.removeAll(completeList)
                            dailyList.addAll(currentList)
                            dailyList.forEach{t ->Log.e("daily",t.name )}
                        },
                        backgroundColor = Color(0xFF7ECA9C),
                        content = {
                            Icon(painter = painterResource(id = R.drawable.ic_accept),contentDescription = "Accept Icon")
                        }, modifier = Modifier.padding(horizontal = 10.dp),
                        shape = CircleShape
                    )
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
            taskNo4.value += 1

            Log.e("zufall",taskNo1.value.toString() + taskNo2.value.toString() + taskNo3.value.toString())
            lock.value = true
        }

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top=10.dp)) {
                Text(text ="Sport", textAlign = TextAlign.Center, style = MaterialTheme.typography.subtitle2, fontStyle = FontStyle.Italic, modifier = Modifier.padding(bottom = 3.dp))
                SurpriseTaskCard(listofcat1[taskNo1.value % listofcat1.size],listofcat1,taskNo1,change1)
                Text(text = "Entspannung", textAlign = TextAlign.Center, style = MaterialTheme.typography.subtitle2, fontStyle = FontStyle.Italic, modifier = Modifier.padding(bottom = 3.dp))
                SurpriseTaskCard(listofcat2[taskNo2.value % listofcat2.size],listofcat2,taskNo2,change2)
                Text(text="Produktiv", textAlign = TextAlign.Center, style = MaterialTheme.typography.subtitle2, fontStyle = FontStyle.Italic, modifier = Modifier.padding(bottom = 3.dp))
                SurpriseTaskCard(listofcat3[taskNo3.value % listofcat3.size],listofcat3,taskNo3,change3)
                Text(text="Kochen", textAlign = TextAlign.Center, style = MaterialTheme.typography.subtitle2, fontStyle = FontStyle.Italic, modifier = Modifier.padding(bottom = 3.dp))
                SurpriseTaskCard(listofcat4[taskNo4.value % listofcat4.size],listofcat4,taskNo4,change4)
            }


    }
}




