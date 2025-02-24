package fr.isen.fougera.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import android.app.Activity

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val event: Event? = intent.getParcelableExtra("event")

        setContent {
            if (event != null) {
                EventDetailScreen(event)
            } else {
                Text("Aucun événement trouvé", fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun EventDetailScreen(event: Event) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = event.title, fontSize = 24.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = event.description, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "📅 ${event.date}", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.Gray)
        Text(text = "📍 ${event.location}", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.Gray)
        Text(text = "🎭 Catégorie : ${event.category}", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Bouton corrigé pour fermer l'Activity
        Button(onClick = { (context as? Activity)?.finish() }) {
            Text("Retour")
        }
    }
}