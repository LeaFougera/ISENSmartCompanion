package fr.isen.fougera.isensmartcompanion

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun CalendarScreen(viewModel: EventViewModel) {
    val context = LocalContext.current
    val eventList by viewModel.events.collectAsState(initial = emptyList())

    val calendar = remember { Calendar.getInstance() }
    val currentMonth = remember { calendar.get(Calendar.MONTH) }
    val currentYear = remember { calendar.get(Calendar.YEAR) }
    val daysInMonth = getDaysInMonth(currentYear, currentMonth)

    var showAddDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "📆 Mon calendrier",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB71C1C),
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {
            items(daysInMonth) { day ->
                DayItem(day, eventList, viewModel)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier.align(Alignment.End),
            containerColor = Color(0xFFB71C1C)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Ajouter un événement", tint = Color.White)
        }
    }

    if (showAddDialog) {
        AddEventDialog(viewModel) { showAddDialog = false }
    }
}

@Composable
fun DayItem(day: Int, eventList: List<Event>, viewModel: EventViewModel) {
    val dayEvents = eventList.filter { it.date.endsWith("-$day") }
    val showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { showDialog.value = true },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("📅 Jour $day", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            if (dayEvents.isNotEmpty()) {
                Text(
                    "📌 ${dayEvents.size} événement(s)",
                    fontSize = 14.sp,
                    color = Color.Red
                )
            }
        }
    }

    if (showDialog.value) {
        showEventDialog(day, dayEvents, viewModel) { showDialog.value = false }
    }
}

@Composable
fun showEventDialog(day: Int, events: List<Event>, viewModel: EventViewModel, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Événements du $day") },
        text = {
            Column {
                events.forEach { event ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(event.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("📍 ${event.location}")
                            Text("📝 ${event.description}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    viewModel.removeEvent(event)
                                    onDismiss() // Ferme le dialogue après suppression
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text("Supprimer", color = Color.White)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) { Text("Fermer") }
        }
    )
}

@Composable
fun AddEventDialog(viewModel: EventViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ajouter un événement") },
        text = {
            Column {
                TextField(value = title, onValueChange = { title = it }, label = { Text("Titre") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                TextField(value = location, onValueChange = { location = it }, label = { Text("Lieu") })
                Button(onClick = {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            date = "$year-${month + 1}-$dayOfMonth"
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }) {
                    Text(if (date.isEmpty()) "📅 Choisir une date" else "📅 Date : $date")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotBlank() && date.isNotBlank()) {
                        viewModel.addEvent(Event(id = System.currentTimeMillis().toInt(), title = title, description = description, date = date, location = location, category = ""))
                        onDismiss()
                    } else {
                        Toast.makeText(context, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Ajouter")
            }
        }
    )
}

// Fonction pour obtenir les jours d'un mois spécifique
fun getDaysInMonth(year: Int, month: Int): List<Int> {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    return (1..daysInMonth).toList()
}