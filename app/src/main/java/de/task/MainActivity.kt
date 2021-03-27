package de.task

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import de.task.DB.*
import de.task.ui.theme.Task2021Theme
import androidx.compose.material.BottomNavigationItem as BottomNavigationItem1
import de.task.screens.*



import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import de.task.DB.Task
import java.time.LocalDate
import java.time.LocalTime
import kotlin.random.Random

object ThemeState{
    var darkModeState : MutableState<Boolean> = mutableStateOf(false)
}

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    private val RC_SIGN_IN: Int = 0
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var currentState = LoginState.NONE
    val list = listOf<Task>()
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent



    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as RoomApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createRequest()


/*
        alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, Receiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }

        alarmMgr?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 60 * 1000,
            alarmIntent
        )


        if(alarmMgr!= null) {
            Log.e("reminder","funktion")
            alarmMgr!!.setExact(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 60 * 1000,
                alarmIntent
            )
        }else{
            Log.e("reminder","null")
        }*/

        val firstname: String = taskViewModel.firstTask
        val listOfTask: List<Task> = taskViewModel.allTasks

        if(listOfTask.isNotEmpty()) {
            val chosenTask = listOfTask.get(
                Random.nextInt(0, listOfTask.size - 1)
            )
            val chosenCompletedTask = CompletedTask(
                0,
                chosenTask.name,
                chosenTask.description,
                chosenTask.duration,
                chosenTask.categoryId,
                chosenTask.pictureId,
                LocalDate.now().toString(),
                LocalTime.now().toString()
            )


            val chosenCompletedTask2 = CompletedTask(
                0,
                chosenTask.name,
                chosenTask.description,
                chosenTask.duration,
                chosenTask.categoryId,
                chosenTask.pictureId,
                LocalDate.now().minusDays(1).toString(),
                LocalTime.now().toString()
            )


            val chosenCompletedTask3 = CompletedTask(
                0,
                chosenTask.name,
                chosenTask.description,
                chosenTask.duration,
                chosenTask.categoryId,
                chosenTask.pictureId,
                LocalDate.now().minusDays(1).toString(),
                LocalTime.now().toString()
            )


            val chosenCompletedTask4 = CompletedTask(
                0,
                chosenTask.name,
                chosenTask.description,
                chosenTask.duration,
                chosenTask.categoryId,
                chosenTask.pictureId,
                LocalDate.now().minusDays(2).toString(),
                LocalTime.now().toString()
            )



            val chosenCompletedTask5 = CompletedTask(
                0,
                chosenTask.name,
                chosenTask.description,
                chosenTask.duration,
                chosenTask.categoryId,
                chosenTask.pictureId,
                LocalDate.now().minusDays(4).toString(),
                LocalTime.now().toString()
            )

         /*   taskViewModel.insert(chosenCompletedTask5)
            taskViewModel.insert(chosenCompletedTask4)
            taskViewModel.insert(chosenCompletedTask3)
            taskViewModel.insert(chosenCompletedTask2)
            taskViewModel.insert(chosenCompletedTask1)*/
        }
        val listOfCompletedTask: List<CompletedTask> = taskViewModel.allCompletedTask



        listOfTask.forEach { Log.e("DatabaseList?","diese Task heißt " + it.name) }
        listOfCompletedTask.forEach { Log.e("DatabaseCompletedList?","diese Task heißt " + it.name) }



        if (firstname != null){Log.e("Datenbank?",firstname)}



        setContent {
            Task2021Theme (taskViewModel.isDark.value){
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //ggf Nullpointer
                    Greeting(mGoogleSignInClient!!, this, listOfTask, listOfCompletedTask,taskViewModel)
                }
            }
        }
    }


    /* If you need to detect changes to a user's auth state that happen outside your app,
    such as access token or ID token revocation, or to perform cross-device sign-in,
    you might also call GoogleSignInClient.silentSignIn when your app starts.*/

    override fun onStart() {
        super.onStart()
        //Check ob User schon eingeloggt ist, wenn nein = null
        val account : GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        //updateUI(account)
    }

    fun createRequest(){
        val gso : GoogleSignInOptions= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        if(GoogleSignIn.getLastSignedInAccount(this) != null){
            currentState = LoginState.LOGGED
        }
    }
}

 fun createNotificationChannel(context: Context) {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library

}

@ExperimentalAnimationApi
@Composable
fun BottomNavigation(navController: NavHostController, items: List<Screen>, mGoogleSignInClient: GoogleSignInClient, context: Context, listOfTask: List<Task>, listCompletedTasks : List<CompletedTask>, taskViewModel: TaskViewModel) {

    val dailyTaskList = remember {mutableListOf<Task>()}
    var currentList = remember {mutableListOf<Task>()}
    Scaffold(bottomBar = {
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
            items.forEach { screen ->
                BottomNavigationItem1(
                    icon = { Icon(painter = painterResource(id = screen.iconId), contentDescription = screen.route) },
                    label = { Text(stringResource(id = screen.resourceId)) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo = navController.graph.startDestination
                            launchSingleTop = true
                        }
                    })
            }
        }
    }) {
        NavHost(navController = navController, startDestination = Screen.Surprise.route, builder = {

            composable(Screen.Streak.route){ CalenderTab(listComletedTasks = listCompletedTasks)}
            composable(Screen.Profil.route){ ProfileScreen(listCompletedTasks, taskViewModel, mGoogleSignInClient, context) }
            composable(Screen.Surprise.route){ surprise(listOfTask, dailyTaskList,currentList)}
            composable(Screen.Daily.route){ DummyCalendar(dailyTaskList,taskViewModel)}
        })
    }
}



@Composable
fun ComponentScreen(currentRoute : String){
    Text(currentRoute .repeat(50), maxLines = 3, textAlign = TextAlign.Center )
}
@ExperimentalAnimationApi
@Composable
fun Greeting(mGoogleSignInClient: GoogleSignInClient, context: Context, listOfTask: List<Task>, listCompletedTasks: List<CompletedTask>, taskViewModel: TaskViewModel) {
    val items = listOf<Screen>(

        Screen.Surprise,
        Screen.Daily,
        Screen.Streak,
        Screen.Profil

    )
    val navController = rememberNavController()
    BottomNavigation(navController = navController, items, mGoogleSignInClient, context, listOfTask, listCompletedTasks, taskViewModel)
}


@ExperimentalAnimationApi
@Composable
fun DefaultPreview(mGoogleSignInClient: GoogleSignInClient, context: Context, listOfTask: List<Task>,listCompletedTasks: List<CompletedTask>, taskViewModel: TaskViewModel) {
    Task2021Theme {
        BottomNavigation(navController =  rememberNavController(), items = listOf<Screen>(
            Screen.Daily,
            Screen.Surprise,
            Screen.Profil
        ), mGoogleSignInClient, context, listOfTask, listCompletedTasks, taskViewModel)
    }
}