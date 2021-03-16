package de.task.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {
    @Query("SELECT * FROM category ORDER BY Category.categoryId ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(category: Category)

    @Query("DELETE FROM category")
    fun deleteAll()


}