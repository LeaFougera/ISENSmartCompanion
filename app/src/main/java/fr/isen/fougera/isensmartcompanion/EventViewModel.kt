package fr.isen.fougera.isensmartcompanion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class EventViewModel : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    fun addEvent(event: Event) {
        _events.value = _events.value + event
    }

    fun getEventsByDate(date: String): List<Event> {
        return _events.value.filter { it.date == date }
    }
}