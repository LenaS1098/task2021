package de.task.DB

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Task(@PrimaryKey(autoGenerate = true) val id: Int,
                val name: String,
                val description: String,
                val duration: Int,
                val categoryId: Int,
                val pictureId: Int) {
}