package de.task

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class Receiver(): BroadcastReceiver() {

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("reminder","klasseFunktion")
        if(context!= null) {
            Log.e("reminder", "klasse")
            val name = "ChannelName"
            val descriptionText = "Beschreibung"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(R.string.channelId.toString(), name, importance).apply {
                    description = descriptionText
                }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                ContextCompat.getSystemService(
                    context,
                    NotificationManager::class.java
                ) as NotificationManager
            notificationManager.createNotificationChannel(channel)


            val mIntent: Intent = Intent(context, MainActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)


            val builder = NotificationCompat.Builder(context, R.string.channelId.toString())
                .setSmallIcon(R.drawable.backicon)
                .setContentTitle("task")
                .setContentText(intent!!.getStringExtra("text"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                notify(2, builder.build())
            }
        }
    }
}

@SuppressLint("UnspecifiedImmutableFlag")
fun setReminder(context: Context, text: String): PendingIntent{
    Log.e("reminder","funktion")
    val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, Receiver::class.java)
    intent.putExtra("text",text)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    alarmMgr.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 15 *1000, pendingIntent)
    return pendingIntent
}

fun cancelReminder(context: Context, intent: PendingIntent){
    val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmMgr.cancel(intent)
}
