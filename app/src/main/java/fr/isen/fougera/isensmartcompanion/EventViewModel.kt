package fr.isen.fougera.isensmartcompanion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class EventViewModel : ViewModel() {

    // On stocke en mémoire tous les événements
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    // Ajout d'un événement
    fun addEvent(event: Event) {
        _events.value = _events.value + event
    }

    // Récupère les événements pour une date donnée (format "yyyy-MM-dd")
    fun getEventsByDate(date: String): List<Event> {
        return _events.value.filter { it.date == date }
    }
}