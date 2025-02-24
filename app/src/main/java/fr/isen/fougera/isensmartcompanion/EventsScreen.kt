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
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

@Parcelize
data class Event(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,  // ✅ Vérifie que ces champs sont bien présents
    val category: String
) : Parcelable

// Liste fictive d’événements
val fakeEvents = listOf(
    Event("1", "Soirée BDE", "Une soirée étudiante organisée par le BDE", "15 Mars 2025", "Salle des fêtes", "Fête"),
    Event("2", "Gala ISEN", "Un événement de prestige pour les étudiants et alumni", "10 Avril 2025", "Hôtel de Ville", "Cérémonie"),
    Event("3", "Journée de Cohésion", "Une journée pour découvrir les associations et clubs", "5 Mai 2025", "Campus ISEN", "Association")
)

@Composable
fun EventsScreen(navController: NavController) {
    var eventList by remember { mutableStateOf<List<Event>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Récupération des données via l'API
    LaunchedEffect(Unit) {
        RetrofitInstance.api.getEvents().enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    eventList = response.body()
                    isLoading = false
                } else {
                    errorMessage = "Échec du chargement des événements"
                    isLoading = false
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                errorMessage = "Erreur : ${t.message}"
                isLoading = false
                Log.e("EventsScreen", "Échec de l'appel API : ${t.message}")
            }
        })
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "📅 Événements ISEN", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            errorMessage != null -> {
                Text(text = errorMessage!!, color = androidx.compose.ui.graphics.Color.Red)
            }
            eventList.isNullOrEmpty() -> {
                Text(text = "Aucun événement trouvé.", fontSize = 18.sp)
            }
            else -> {
                LazyColumn {
                    items(eventList!!) { event ->
                        EventItem(event, navController)
                    }
                }
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