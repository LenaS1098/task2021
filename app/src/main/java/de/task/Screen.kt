package de.task

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (val route: String, @StringRes val resourceId: Int, val iconId: Int){
    object Streak : Screen("streak", R.string.streak, R.drawable.ic_streak)
    object Daily : Screen("daily",R.string.home, R.drawable.ic_home)
    object Surprise : Screen("surprise",R.string.surprise,R.drawable.ic_celebration)
    object Profil : Screen("profil",R.string.profil, R.drawable.ic_profile)
}