package fr.isen.fougera.isensmartcompanion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Modèle de données pour un événement
data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val location: String,  // ✅ Vérifie que cette ligne est bien présente
    val category: String
)

// Liste fictive d’événements
val fakeEvents = listOf(
    Event(1, "Soirée BDE", "Une soirée étudiante organisée par le BDE", "15 Mars 2025", "Salle des fêtes", "Fête"),
    Event(2, "Gala ISEN", "Un événement de prestige pour les étudiants et alumni", "10 Avril 2025", "Hôtel de Ville", "Cérémonie"),
    Event(3, "Journée de Cohésion", "Une journée pour découvrir les associations et clubs", "5 Mai 2025", "Campus ISEN", "Association")
)

@Composable
fun EventsScreen(navController: NavController) { // ✅ Accepte `navController`
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "📅 Événements ISEN", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(fakeEvents) { event ->
                EventItem(event, navController) // ✅ Passe `navController` à EventItem
            }
        }
    }
}

@Composable
fun EventItem(event: Event, navController: NavController) { // ✅ Accepte `navController`
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("eventDetail/${event.id}") }, // ✅ Navigue vers les détails
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = event.description)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "📅 ${event.date}", fontSize = 14.sp, color = androidx.compose.ui.graphics.Color.Gray)
        }
    }
}