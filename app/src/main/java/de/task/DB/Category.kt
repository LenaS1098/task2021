package de.task.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(@PrimaryKey(autoGenerate = true)val categoryId: Int,
                    val cName: String,
                    val prefDayTime: String,
                    val iconId: Int) {

}