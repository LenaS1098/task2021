package de.task.screens

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
import de.task.R
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.YearMonth

@Composable
fun ProfileScreen(){
    Column() {
        ProfileHeader()
        PageContent()
    }

}

@Composable
fun CalenderTab(){
    val monthFlow = MutableStateFlow(YearMonth.now())

    val selectionSet = MutableStateFlow(setOf<CalposeDate>())
    selectionSet.value = getStreakDateS()

    Spacer(modifier = Modifier.padding(7.dp))
    CalendarStreak(monthFlow = monthFlow, selectionSet = selectionSet)
}

@Composable
fun StatTab(){
    Boxes()
}

@Composable
fun SettingTab(){

        val checkedState = remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(color = colors.background)
        ) {
            Box(
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(
                    text = "Einstellung mit einer langen Beschreibung",
                    fontStyle = FontStyle.Italic
                )

            }
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {

                Checkbox(
                    checked = checkedState.value,
                    modifier = Modifier.padding(start = 30.dp),
                    onCheckedChange = { checkedState.value = it })
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(text = "Kurze Einstellung", fontStyle = FontStyle.Italic)

            }
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {

                Checkbox(
                    checked = checkedState.value,
                    modifier = Modifier.padding(start = 30.dp),
                    onCheckedChange = { checkedState.value = it })
            }

        }
}

@Composable
fun PageContent(){

    val tabState = remember { mutableStateOf(0) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly){
        Button(onClick = { tabState.value = 0} , colors = ButtonDefaults.buttonColors(backgroundColor = colors.primaryVariant), shape = CircleShape){
            Text(text = "Calender")
        }
        Button(onClick = { tabState.value = 1} , colors = ButtonDefaults.buttonColors(backgroundColor = colors.primaryVariant), shape = CircleShape){
            Text(text = "Stats")
        }
        Button(onClick = { tabState.value = 2} , colors = ButtonDefaults.buttonColors(backgroundColor = colors.primaryVariant), shape = CircleShape){
            Text(text = "Settings")
        }
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)) {
        when(tabState.value){
            0 -> {
                CalenderTab()
            }
            1 -> { StatTab(
            )}
            2 -> {
                SettingTab()}
            else -> {
                Text("Calender Tab", modifier = Modifier.padding(top= 10.dp))
            }
        }
    }

}
@Composable
fun ProfileHeader(){
    Surface(color = colors.primary) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                //bitmap = ImageBitmap.imageResource(id = R.drawable.profile),
                contentDescription = "profileIcon",
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .border(
                        border = BorderStroke(1.dp, color = colors.onPrimary),
                        shape = CircleShape
                    )
                    .clip(shape = CircleShape)
                    .padding(8.dp),
                colorFilter = ColorFilter.tint(color = colors.onPrimary),
                painter = painterResource(id = R.drawable.ic_profile)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text= "ProfilName", style = MaterialTheme.typography.h6, color = colors.onPrimary)
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text= "Profil Email", style = MaterialTheme.typography.caption, color = colors.onPrimary)


            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
               Icon(painter = painterResource(id = R.drawable.ic_logout),"icon")
            }


        }

    }
}

