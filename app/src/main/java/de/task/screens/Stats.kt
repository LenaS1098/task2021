package de.task.screens

import android.app.slice.Slice
import android.os.Parcel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import de.task.DB.CompletedTask
import de.task.DB.Task

@Composable
fun Boxes(listCompletedTasks: List<CompletedTask>) {
        Spacer(modifier = Modifier.padding(15.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .size(160.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(textAlign = TextAlign.Center, text = "Streak", fontStyle = FontStyle.Italic, fontSize = 18.sp)
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(textAlign = TextAlign.Center, text = "00", style = MaterialTheme.typography.h4)
                }
            }
            Card(
                modifier = Modifier
                    .size(160.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary,

            )
            {
                var totalDuration = 0
                listCompletedTasks.forEach{
                    totalDuration += it.duration
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(textAlign = TextAlign.Center, text = "Total Duration", fontStyle = FontStyle.Italic, fontSize = 18.sp)
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(textAlign = TextAlign.Center, text = "$totalDuration", style = MaterialTheme.typography.h4)
                }
            }
        }
        Spacer(modifier = Modifier.padding(15.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .size(160.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(textAlign = TextAlign.Center, text = "Total Tasks", fontStyle = FontStyle.Italic, fontSize = 18.sp)
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(textAlign = TextAlign.Center, text = "${listCompletedTasks.size}", style = MaterialTheme.typography.h4)
                }



            }
            Card(
                modifier = Modifier
                    .size(160.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(textAlign = TextAlign.Center, text = "Favorite Categories", fontStyle = FontStyle.Italic, fontSize = 18.sp)
                    Spacer(modifier = Modifier.padding(4.dp))

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(textAlign = TextAlign.Center, text = "PieChart", fontSize = 18.sp)
                }
            }
        }

}
