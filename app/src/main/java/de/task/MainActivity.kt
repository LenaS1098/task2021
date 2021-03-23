package de.task

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalTime
import kotlin.random.Random

import de.task.screens.*

class MainActivity : ComponentActivity() {

    private val RC_SIGN_IN: Int = 0
    private var mGoogleSignInClient: GoogleSignInClient? = null
     private var currenState = LoginState.NONE


    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as RoomApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createRequest()


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






            taskViewModel.insert(chosenCompletedTask)
        }
        val listOfCompletedTask: List<CompletedTask> = taskViewModel.allCompletedTask

        listOfTask.forEach { Log.e("DatabaseList?","diese Task heißt " + it.name) }
        listOfCompletedTask.forEach { Log.e("DatabaseCompletedList?","diese Task heißt " + it.name) }


        if (firstname != null){Log.e("Datenbank?",firstname)}

        setContent {
            Task2021Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //ggf Nullpointer
                    Greeting(mGoogleSignInClient!!, this, listOfTask)
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
            currenState = LoginState.LOGGED
        }
    }
}


@Composable
fun bottomNavigation(navController: NavHostController, items: List<Screen>, mGoogleSignInClient: GoogleSignInClient, context: Context, listOfTask: List<Task>) {

    Scaffold(bottomBar = {
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
            items.forEach { screen ->
                BottomNavigationItem1(
                    icon = { Icon(imageVector = screen.icon, contentDescription = screen.route) },
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
        NavHost(navController = navController, startDestination = Screen.Daily.route, builder = {
            composable(Screen.Daily.route){ componentScreen(currentRoute = Screen.Daily.route)}
            composable(Screen.Streak.route){ streak()}
            composable(Screen.Surprise.route){ surprise()}
            composable(Screen.Settings.route){ componentScreen(currentRoute = Screen.Settings.route)}
            composable(Screen.Daily.route){ DummyCalendar(listOfTask)}
            composable(Screen.Streak.route){ componentScreen(currentRoute = Screen.Streak.route)}
            composable(Screen.Surprise.route){ componentScreen(currentRoute = Screen.Surprise.route)}
            composable(Screen.Settings.route){ settingScreen(mGoogleSignInClient, context) }
        })
    }
}



@Composable
fun componentScreen(currentRoute : String){
    Text(currentRoute .repeat(50), maxLines = 3, textAlign = TextAlign.Center )
}
@Composable
fun Greeting(mGoogleSignInClient: GoogleSignInClient, context: Context, listOfTask: List<Task>) {
    val items = listOf<Screen>(
        Screen.Daily,
        Screen.Streak,
        Screen.Surprise,
        Screen.Settings
    )
    val navController = rememberNavController()
    bottomNavigation(navController = navController, items, mGoogleSignInClient, context, listOfTask)
}


@Composable
fun DefaultPreview(mGoogleSignInClient: GoogleSignInClient, context: Context, listOfTask: List<Task>) {
    Task2021Theme {
        bottomNavigation(navController =  rememberNavController(), items = listOf<Screen>(
            Screen.Daily,
            Screen.Streak,
            Screen.Surprise,
            Screen.Settings
        ), mGoogleSignInClient, context, listOfTask)
    }
}