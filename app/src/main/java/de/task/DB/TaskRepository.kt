package de.task.DB

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTask: List<Task> = taskDao.getAllTasks()

    val firstTask: String = taskDao.getFirstTaskName()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }
}