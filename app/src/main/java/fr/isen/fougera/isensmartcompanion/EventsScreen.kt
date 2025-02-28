package fr.isen.fougera.isensmartcompanion

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun EventsScreen(navController: NavController) {
    var eventList by remember { mutableStateOf<List<Event>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Récupération des événements via l'API
    LaunchedEffect(Unit) {
        RetrofitInstance.api.getEvents().enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    eventList = response.body() ?: emptyList()
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
        Text(
            text = "📅 Événements ISEN",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB71C1C) // 🔴 Rouge ISEN
        )
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> Text(text = errorMessage!!, color = Color.Red)
            eventList.isNullOrEmpty() -> Text(text = "Aucun événement trouvé.", fontSize = 18.sp)
            else -> LazyColumn {
                items(eventList!!) { event ->
                    EventItem(event, navController)
                }
            }
        }
    }
}

@Composable
fun EventItem(event: Event, navController: NavController) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(context, EventDetailActivity::class.java).apply {
                    putExtra("event_id", event.id.toString())
                    putExtra("event_title", event.title)
                    putExtra("event_description", event.description)
                    putExtra("event_date", event.date)
                    putExtra("event_location", event.location)
                    putExtra("event_category", event.category)
                }
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp), // Arrondir les coins
        //modifier = Modifier.background(Color(0xFFF1F1F1)) // Appliquer la couleur de fond via le Modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = event.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB71C1C) // 🔴 Rouge ISEN
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "📅 ${event.date}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}