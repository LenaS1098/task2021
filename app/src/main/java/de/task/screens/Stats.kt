package de.task.screens

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
import de.task.DB.Task

@Composable
fun Boxes() {
        Spacer(modifier = Modifier.padding(15.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .size(125.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary
            )
            {
                Text(textAlign = TextAlign.Center, text = "A")
            }
            Card(
                modifier = Modifier
                    .size(125.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary,

            )
            {
                Text(textAlign = TextAlign.Center, text = "B")
            }
        }
        Spacer(modifier = Modifier.padding(15.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .size(125.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(textAlign = TextAlign.Center, text = "Completed Tasks", fontStyle = FontStyle.Italic, fontSize = 18.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(textAlign = TextAlign.Center, text = "3", style = MaterialTheme.typography.caption)
                }


            }
            Card(
                modifier = Modifier
                    .size(125.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary
            )
            {
                Text(textAlign = TextAlign.Center, text = "D")
            }
        }

}
