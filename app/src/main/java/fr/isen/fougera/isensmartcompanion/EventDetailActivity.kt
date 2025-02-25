package fr.isen.fougera.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Tag
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
import android.app.Activity
import androidx.compose.ui.graphics.vector.ImageVector

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Ajoute un peu de padding autour
        contentAlignment = Alignment.Center // Centrage des éléments dans la Box
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() // Permet de limiter la hauteur du Column pour centrer
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centrer tout le contenu verticalement
        ) {
            // Titre de l'événement
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB71C1C), // Rouge ISEN
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Carte de la description de l'événement
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

            // Informations supplémentaires sur l'événement (Date, Lieu, Catégorie)
            InfoRow(icon = Icons.Filled.Event, label = "Date", value = date)
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(icon = Icons.Filled.LocationOn, label = "Lieu", value = location)
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(icon = Icons.Filled.Tag, label = "Catégorie", value = category)

            // Bouton retour
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
           // .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.small)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFFB71C1C) // Rouge ISEN
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

@Preview(showBackground = true)
@Composable
fun EventDetailScreenPreview() {
    EventDetailScreen(
        title = "Gala ISEN",
        description = "Soirée prestigieuse organisée par le BDE pour célébrer les réussites de l'année.",
        date = "10 Décembre 2024",
        location = "Palais Neptune, Toulon",
        category = "BDE"
    )
}