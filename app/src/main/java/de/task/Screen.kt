package de.task

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (val route: String, @StringRes val resourceId: Int, val icon: ImageVector){
    object Streak : Screen("streak", R.string.streak, Icons.Filled.Call)
    object Daily: Screen("daily",R.string.home, Icons.Filled.Home)
    object Surprise : Screen("surprise",R.string.surprise, Icons.Filled.Warning)
    object Settings : Screen("settings",R.string.settings, Icons.Filled.Settings)
}