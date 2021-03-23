package de.task.DB

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao, private val userDao: UserDao, private val completedTaskDao: CompletedTaskDao) {
    val allTask: List<Task> = taskDao.getAllTasks()
    val allCompletedTask: List<CompletedTask> = completedTaskDao.getAllCompletedTasks()

    val firstTask: String = taskDao.getFirstTaskName()

    val getUser : User = userDao.getSpecificUser("task.testuser01@gmail.com")

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(completedTask: CompletedTask) {
        completedTaskDao.insert(completedTask)
    }

}