package fr.isen.fougera.isensmartcompanion

import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.vector.ImageVector

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupération des données depuis l'Intent
        val eventTitle = intent.getStringExtra("event_title") ?: "Événement"
        val eventDescription = intent.getStringExtra("event_description") ?: "Aucune description disponible"
        val eventDate = intent.getStringExtra("event_date") ?: "Date inconnue"
        val eventLocation = intent.getStringExtra("event_location") ?: "Lieu inconnu"
        val eventCategory = intent.getStringExtra("event_category") ?: "Catégorie inconnue"

        setContent {
            EventDetailScreen(eventTitle, eventDescription, eventDate, eventLocation, eventCategory)
        }
    }
}

@Composable
fun EventDetailScreen(
    title: String,
    description: String,
    date: String,
    location: String,
    category: String
) {
    val context = LocalContext.current
    val sharedPreferencesManager = SharedPreferencesManager(context)
    var isNotified by remember { mutableStateOf(sharedPreferencesManager.areNotificationsEnabled()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB71C1C),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = description,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }
            }

            InfoRow(icon = Icons.Filled.Event, label = "Date", value = date)
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(icon = Icons.Filled.LocationOn, label = "Lieu", value = location)
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(icon = Icons.Filled.Tag, label = "Catégorie", value = category)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    isNotified = !isNotified
                    sharedPreferencesManager.setNotificationsEnabled(isNotified)
                    if (isNotified) {
                        scheduleNotification(context)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isNotified) "Désabonner des notifications" else "S'abonner aux notifications",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { (context as? Activity)?.finish() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C))
            ) {
                Text("Retour", color = Color.White)
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFFB71C1C)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

fun scheduleNotification(context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "event_reminders"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Event Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val notificationIntent = Intent(context, EventDetailActivity::class.java)
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

    val notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle("Rappel d'événement")
        .setContentText("L'événement que vous avez suivi arrive bientôt!")
        .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    // Planifier la notification après 10 secondes
    GlobalScope.launch {
        delay(50000) // Délai de 10 secondes
        NotificationManagerCompat.from(context).notify(1, notification) // Utilisation d'un ID unique
    }
}