package de.task.DB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Task::class,Category::class,User::class),version = 1,exportSchema =false)
public abstract class TaskRoomDatabase: RoomDatabase() {

    abstract fun TaskDao(): TaskDao

}
