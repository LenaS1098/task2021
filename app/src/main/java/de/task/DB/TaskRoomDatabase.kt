package de.task.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Types.NULL

@Database(entities = arrayOf(Task::class,Category::class,User::class,CompletedTask::class),version = 1,exportSchema =false)
public abstract class TaskRoomDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
    abstract fun userDao(): UserDao
    abstract fun completedTaskDao(): CompletedTaskDao


    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {


                        var taskDao = database.taskDao()
                        var userDao = database.userDao()

                        // Delete all content here.
                        taskDao.deleteAll()
                        userDao.deleteAll()

                        //alle basisuser
                        var user = User(0, "Max", "task.testuser01@gmail.com")
                        userDao.insert(user)



                        // alle basistasks
                        var task = Task(0, "Liegestütze", "Mache 15 Liegestütze! Wenn du keine Kraft hast, mach sie Gegen eine Wand. Nächstes mal gegen einen Tisch, dann auf den Knien.", 5, 1, 1,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Situps", "Mache 15 Situps", 5, 1, 2,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Joggen", "Welchen Park kennst du noch nicht? Geh .. Jogg los und enrkunde ihn", 40, 1, 3,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Klimmzüge", "Nur so viele du kannst!", 5, 1, 4,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Seilspringen", "Versuche in einer Minute so viele Seilsprünge wie du kannst", 1, 1, 5,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Fahrrad fahren", "Fahr mal ne Runde mit dem Fahrrad", 60, 1, 17,null,null)
                        taskDao.insert(task)




                        task = Task(0, "Sprache lernen", "Der Appstore bietet viele Möglichkeiten, um eine neue Sprache zu lernen. Nehm dir dochmal die 5 Minuten, die du sonst nie findest", 5, 3, 6,
                            null,null)
                        taskDao.insert(task)
                        task = Task(0, "Pflanzen", "Besorg dir eine Zimmerpflanze oder kümmer dich um bereits vorhandene", 5, 3, 7,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Yoga", "Finde deine Innere Mitte, und suche dir eine Yoga Übung", 20, 3, 8,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Meditation", "Suche dir eine Meditationsübung und finde etwas innere Ruhe ", 20, 3, 9,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Lesen", "Such dir ein Buch und lese ein bisschen", 30, 3, 10,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Spazieren", "Beweg dich ein bisschen, aber denk an Social <distancing", 40, 3, 11,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Malen", "Lass deiner Kreativität freien lauf", 20, 3, 12,null,null)
                        taskDao.insert(task)

                        task = Task(0, "Staubsaugen", "Was getan werden muss, muss getan werden", 40, 4, 13 ,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Boden Wischen", "Was getan werden muss, muss getan werden", 40, 4, 14 ,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Einkauf", "Fülle den Kühlschrank wieder auf", 120, 4, 15,null,null)
                        taskDao.insert(task)
                        task = Task(0, "Emails", "Nimm dir Zeit, deine Mails zu beantworten oder Termine zu buchen", 40, 4, 16,null,null)
                        taskDao.insert(task)

                        task = Task(0, "Kuchen backen", "Ob für dich, zum Verschenken oder zum Teilen! Such dir ein neues Rezept auf kochchef.io aus und backe diesen Kuchen!", 60, 5, 18,null,null)
                        taskDao.insert(task)
                        task = Task(0, "English Breakfast", "2 Eier, Speck, gebackene Dosenbohnen & Toast. Easy und schnell!", 15, 5, 19,null,null)
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



