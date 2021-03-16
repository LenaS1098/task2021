package de.task.DB

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RoomApplication : Application(){

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { TaskRoomDatabase.getDatabase(this,applicationScope) }

}