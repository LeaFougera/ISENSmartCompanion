package fr.isen.fougera.isensmartcompanion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

@Composable
fun CalendarHeader(yearMonth: YearMonth, onMonthChange: (YearMonth) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onMonthChange(yearMonth.minusMonths(1)) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("<")
        }

        Text(
            text = "${yearMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${yearMonth.year}",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = { onMonthChange(yearMonth.plusMonths(1)) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(">")
        }
    }
}

@Composable
fun AgendaScreen(viewModel: EventViewModel = viewModel()) {
    val today = LocalDate.now()
    val currentMonth = remember { mutableStateOf(YearMonth.of(today.year, today.month)) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val eventsForSelectedDate = remember { mutableStateOf(emptyList<Event>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        CalendarHeader(currentMonth.value) { newMonth -> currentMonth.value = newMonth }
        CalendarGrid(currentMonth.value, today) { date ->
            selectedDate = date
            eventsForSelectedDate.value = viewModel.getEventsByDate(date.toString())
            showDialog = true
        }
    }

    if (showDialog && selectedDate != null) {
        EventDialog(
            date = selectedDate!!,
            events = eventsForSelectedDate.value,
            onDismiss = { showDialog = false },
            onSave = { event ->
                viewModel.addEvent(event)
                eventsForSelectedDate.value = viewModel.getEventsByDate(selectedDate!!.toString())
                showDialog = false
            }
        )
    }
}

@Composable
fun CalendarGrid(yearMonth: YearMonth, today: LocalDate, onDateClick: (LocalDate) -> Unit) {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(yearMonth.year, yearMonth.month, 1).dayOfWeek.value % 7
    val days = List(firstDayOfMonth) { "" } + (1..daysInMonth).map { it.toString() }

    LazyVerticalGrid(columns = GridCells.Fixed(7), modifier = Modifier.padding(8.dp)) {
        items(days) { day ->
            val date = day.toIntOrNull()?.let { LocalDate.of(yearMonth.year, yearMonth.month, it) }
            val isToday = date == today

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isToday) Color.Red else Color.Transparent)
                    .clickable { date?.let(onDateClick) },
                contentAlignment = Alignment.Center
            ) {
                if (day.isNotEmpty()) {
                    Text(
                        text = day,
                        color = if (isToday) Color.White else Color.Black,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun EventDialog(date: LocalDate, events: List<Event>, onDismiss: () -> Unit, onSave: (Event) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.padding(16.dp), shape = RoundedCornerShape(8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Événements pour ${date.dayOfMonth}/${date.monthValue}/${date.year}", style = MaterialTheme.typography.headlineMedium)

                if (events.isNotEmpty()) {
                    events.forEach { event ->
                        Text("- ${event.title}: ${event.description}", style = MaterialTheme.typography.bodyMedium)
                    }
                } else {
                    Text("Aucun événement trouvé", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Ajouter un nouvel événement", style = MaterialTheme.typography.headlineSmall)
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Titre") })
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Lieu") })
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Catégorie") })

                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = onDismiss) { Text("Annuler") }
                    Button(onClick = {
                        onSave(Event(UUID.randomUUID().toString(), title, description, date.toString(), location, category))
                    }) { Text("Sauvegarder") }
                }
            }
        }
    }
}