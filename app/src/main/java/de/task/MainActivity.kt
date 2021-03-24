package de.task

import android.content.Context
import android.os.Bundle
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


class MainActivity : ComponentActivity() {

    private val RC_SIGN_IN: Int = 0
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var currenState = LoginState.NONE
    val list = listOf<Task>()

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as RoomApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createRequest()

        val firstname: String = taskViewModel.firstTask
        val listOfTask: List<Task> = taskViewModel.allTasks

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
fun BottomNavigation(navController: NavHostController, items: List<Screen>, mGoogleSignInClient: GoogleSignInClient, context: Context, listOfTask: List<Task>) {

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
            composable(Screen.Daily.route){ DummyCalendar(listOfTask)}
            //composable(Screen.Streak.route){ stats(listOfTask)}
            composable(Screen.Surprise.route){ ComponentScreen(currentRoute = Screen.Surprise.route)}
            composable(Screen.Profil.route){ ProfileScreen() }
        })
    }
}


@Composable
fun ComponentScreen(currentRoute : String){
    Text(currentRoute .repeat(50), maxLines = 3, textAlign = TextAlign.Center )
}
@Composable
fun Greeting(mGoogleSignInClient: GoogleSignInClient, context: Context, listOfTask: List<Task>) {
    val items = listOf<Screen>(
        Screen.Daily,
        Screen.Surprise,
        Screen.Profil
    )
    val navController = rememberNavController()
    BottomNavigation(navController = navController, items, mGoogleSignInClient, context, listOfTask)
}


@Composable
fun DefaultPreview(mGoogleSignInClient: GoogleSignInClient, context: Context, listOfTask: List<Task>) {
    Task2021Theme {
        BottomNavigation(navController =  rememberNavController(), items = listOf<Screen>(
            Screen.Daily,
            Screen.Surprise,
            Screen.Profil
        ), mGoogleSignInClient, context, listOfTask)
    }
}