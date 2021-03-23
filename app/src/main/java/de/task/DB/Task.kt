package de.task.DB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*


@Entity
data class Task(@PrimaryKey(autoGenerate = true) val id: Int,
                val name: String,
                val description: String,
                val duration: Int,
                val categoryId: Int,
                val pictureId: Int,
                val date: String?,
                val begin: String?
                ) {
}