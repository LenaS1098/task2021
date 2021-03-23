package de.task.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface CompletedTaskDao{
    @Query("SELECT * FROM completedtask ORDER BY CompletedTask.id ASC")
    fun getAllCompletedTasks(): List<CompletedTask>

    @Query("SELECT CompletedTask.name FROM completedtask WHERE CompletedTask.id = 1")
    fun getFirstTaskName(): String

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(task:CompletedTask)

    @Query("DELETE FROM completedtask")
    fun deleteAll()
}