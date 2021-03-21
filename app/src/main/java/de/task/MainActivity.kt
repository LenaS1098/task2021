package de.task

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import de.task.DB.*
import de.task.ui.theme.Task2021Theme
import androidx.compose.material.BottomNavigationItem as BottomNavigationItem1

class MainActivity : ComponentActivity() {


    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as RoomApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var firstname: String? = taskViewModel.firstTask.value
        var listOfTask: List<Task>? = taskViewModel.allTasks.value


        taskViewModel.allTasks.observe(this, Observer { listOfTask = taskViewModel.allTasks.value
        })




        listOfTask?.forEach { Log.e("DatabaseList?","diese Task heißt " + it.name) }
            ?: Log.e("DatabaseList?","leer")


        if (firstname != null) {
            Log.e("Datenbank?",firstname)
        }
        else{Log.e("Datenbank?","Data is missing")}
        
        setContent {
            Task2021Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()

                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        var firstname: String? = taskViewModel.firstTask.value
        var listOfTask: List<Task>? = taskViewModel.allTasks.value

        listOfTask?.forEach { Log.e("DatabaseList?", "diese Task heißt " + it.name) }
            ?: Log.e("DatabaseList?", "leer")


        if (firstname != null) {
            Log.e("Datenbank?", firstname)
        } else {
            Log.e("Datenbank?", "Data is missing")
        }
    }

}

    @Composable
    fun bottomNavigation(navController: NavHostController, items: List<Screen>) {

        Scaffold(bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                items.forEach { screen ->
                    BottomNavigationItem1(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.route
                            )
                        },
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
            NavHost(

                navController = navController,
                startDestination = Screen.Daily.route,
                builder = {
                    composable(Screen.Daily.route) { componentScreen(currentRoute = Screen.Daily.route) }
                    composable(Screen.Streak.route) { componentScreen(currentRoute = Screen.Streak.route) }
                    composable(Screen.Surprise.route) { componentScreen(currentRoute = Screen.Surprise.route) }
                    composable(Screen.Settings.route) { componentScreen(currentRoute = Screen.Settings.route) }
                })
        }
    }


    @Composable
    fun componentScreen(currentRoute: String) {
        Text(currentRoute.repeat(50), maxLines = 3, textAlign = TextAlign.Center)
    }

    @Composable
    fun Greeting() {
        val items = listOf<Screen>(
            Screen.Daily,
            Screen.Streak,
            Screen.Surprise,
            Screen.Settings
        )
        val navController = rememberNavController()
        bottomNavigation(navController = navController, items)
    }

    @Composable
    fun DefaultPreview() {
        Task2021Theme {
            bottomNavigation(
                navController = rememberNavController(), items = listOf<Screen>(
                    Screen.Daily,
                    Screen.Streak,
                    Screen.Surprise,
                    Screen.Settings
                )
            )
        }
    }
