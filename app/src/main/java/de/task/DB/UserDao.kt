package de.task.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY user.id ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Query("DELETE FROM User")
    fun deleteAll()

}