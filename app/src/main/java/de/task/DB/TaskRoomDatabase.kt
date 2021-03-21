package de.task.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Task::class,Category::class,User::class),version = 1,exportSchema =false)
public abstract class TaskRoomDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao


    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {


                        var taskDao = database.taskDao()

                        // Delete all content here.
                        taskDao.deleteAll()

                        // Add sample words.
                        var task = Task(0, "Liegestütze", "Mache 15 Liegestütze", 5, 1, 1)
                        taskDao.insert(task)
                        task = Task(0, "Situps", "Mache 15 Situps", 5, 1, 2)
                        taskDao.insert(task)

                    }
                }

            }
        }
        companion object {
            @Volatile
            private var INSTANCE: TaskRoomDatabase? = null

            fun getDatabase(
                context: Context,
                scope: CoroutineScope
            ): TaskRoomDatabase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskRoomDatabase::class.java,
                        "task_database"
                    )
                        .addCallback(WordDatabaseCallback(scope))
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
            }
        }
    }



