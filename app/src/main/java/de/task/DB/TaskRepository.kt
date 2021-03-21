package de.task.DB

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTask: Flow<List<Task>> = taskDao.getAllTasks()

    val firstTask: Flow<String> = taskDao.getFirstTaskName()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }
}