package fr.isen.fougera.isensmartcompanion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaEventScreen(
    date: LocalDate,
    events: List<Event>,
    onClose: () -> Unit,
    onAddEvent: (Event) -> Unit
) {
    var showAddEventDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Événements du ${date.dayOfMonth}/${date.monthValue}/${date.year}") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (events.isEmpty()) {
                Text("Aucun événement prévu.", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn {
                    items(events) { event ->
                        EventItem(event)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showAddEventDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ajouter un événement")
            }
        }
    }

    if (showAddEventDialog) {
        AddEventDialog(date = date, onDismiss = { showAddEventDialog = false }, onSave = onAddEvent)
    }
}

@Composable
fun EventItem(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )

            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Place, contentDescription = "Lieu", tint = Color.Red)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = event.location, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Category, contentDescription = "Catégorie", tint = Color.Red)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = event.category, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            }
        }
    }
}