package de.task.screens

import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import de.task.DB.Task


@Composable
fun statHeader(){
    Column(modifier = Modifier
        .background(MaterialTheme.colors.primary)
        .height(100.dp)
        .clip(CircleShape)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "Statistiken", color = MaterialTheme.colors.onPrimary, fontSize = 25.sp , textAlign = TextAlign.Left, modifier = Modifier.padding(start= 5.dp))
    }
}


@Composable
fun statBoxes(completedTasks: List<Task>){


}

@Composable
fun stats(completedTasks : List <Task>){
    statHeader()
}