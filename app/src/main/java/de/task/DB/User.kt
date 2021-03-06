package de.task.DB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class User(@PrimaryKey(autoGenerate = true) val id: Int, val name: String, val email: String)