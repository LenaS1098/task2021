package de.task.screens
import de.task.*

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.model.CalposeDate
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import de.task.DB.CompletedTask
import de.task.DB.Task
import de.task.DB.TaskViewModel
import de.task.R
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.YearMonth

@Composable
fun ProfileScreen(listCompletedTasks: List<CompletedTask>, taskViewModel: TaskViewModel, mGoogleSignInClient: GoogleSignInClient, context: Context){
    Column() {
        LoginHeader(mGoogleSignInClient = mGoogleSignInClient , context = context)
        PageContent(listCompletedTasks, taskViewModel, context)
    }

}


@Composable
fun PageContent(listCompletedTasks: List<CompletedTask>, taskViewModel: TaskViewModel, context: Context){

    val tabState = remember { mutableStateOf(0) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { tabState.value = 0 },
                colors = ButtonDefaults.buttonColors(backgroundColor = colors.secondary),
                shape = CircleShape) {
                Text(text = "Stats")
            }
            Button(onClick = { tabState.value = 1 },
                colors = ButtonDefaults.buttonColors(backgroundColor = colors.secondary),
                shape = CircleShape) {
                Text(text = "Settings")
            }

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            when (tabState.value) {
                0 -> {
                    Boxes(listCompletedTasks)
                }
                1 -> {
                    SettingTab(taskViewModel, context)
                }
                else -> {
                    Boxes(listCompletedTasks)
                }
            }
        }

}


