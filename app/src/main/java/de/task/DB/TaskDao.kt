package de.task.DB

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY Task.id ASC")
    fun getAllTasks(): List<Task>

    @Query("SELECT Task.name FROM task WHERE Task.id = 1")
    fun getFirstTaskName(): String



    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(task:Task)

    @Query("DELETE FROM task")
    fun deleteAll()
}