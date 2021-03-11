package de.task.DB

import androidx.room.Dao
import androidx.room.Query


@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY Task.id ASC")
    fun getAllTasks(): List<Task>


}