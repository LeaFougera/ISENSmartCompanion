package fr.isen.fougera.isensmartcompanion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {

    fun scheduleNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "event_reminders"

        // Créer un canal de notification pour Android 8.0 (API 26) et supérieur
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Event Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(context, EventDetailActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Rappel d'événement")
            .setContentText("L'événement que vous avez suivi arrive bientôt!")
            .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Planifier la notification après 10 secondes
        GlobalScope.launch(Dispatchers.Main) {
            delay(10000) // Attendre 10 secondes
            NotificationManagerCompat.from(context).notify(0, notification)
        }
    }
}