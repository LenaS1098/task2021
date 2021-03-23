package de.task.screens



import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.task.DB.Task
import de.task.R


@Immutable
data class ExpandableCardModel(val id: Int, val title: String)

@Composable
fun TaskCard(task: Task){
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

                }
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.task),
                contentDescription = "TaskImage",
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .size(100.dp)
                    .padding(start = 10.dp)
            )
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = task.name, fontSize = 24.sp)
                Text(text = "Dauer:  "+ task.duration)
                Text(text = "Kategorie:  ${task.categoryId}")
            }

        }
    }
}

@Composable
fun DummyCalendar(taskList: List<Task>) {


    val currentList = taskList

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)
        ) {
            Image(bitmap = ImageBitmap.imageResource(id = R.drawable.backicon), contentDescription = "backIcon",
                modifier = Modifier
                    .size(20.dp)
            )
            Text(text = "heutiges Datum als String?", modifier = Modifier.padding(start = 10.dp, end = 10.dp) )
            Image(bitmap = ImageBitmap.imageResource(id = R.drawable.nexticon), contentDescription = "nextIcon",
                modifier = Modifier
                    .size(30.dp)
            )
        }

    }

    LazyColumn{ items(currentList){
        task -> TaskCard(task = task)
    }
        
    }
}

