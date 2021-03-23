package de.task.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY user.id ASC")
    fun getAllUser(): List<User>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Query("SELECT * FROM user Where user.email = :searchedMail")
    fun getSpecificUser(searchedMail: String?): User

    @Query("DELETE FROM User")
    fun deleteAll()

}