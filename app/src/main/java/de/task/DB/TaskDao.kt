package de.task.DB

import androidx.room.*


@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY Task.id ASC")
    fun getAllTasks(): List<Task>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(task:Task)

    @Query("DELETE FROM task")
    fun deleteAll()
}