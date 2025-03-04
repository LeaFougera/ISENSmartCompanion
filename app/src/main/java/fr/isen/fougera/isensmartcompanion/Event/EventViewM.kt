package fr.isen.fougera.isensmartcompanion.Event

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventViewM(application: Application) : AndroidViewModel(application) {
    private val repository = EventRepository(application)

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    fun addEvent(event: Event) {
        viewModelScope.launch {
            repository.insertEvent(event)
            loadEventsByDate(event.date)
        }
    }

    fun loadEventsByDate(date: String) {
        viewModelScope.launch {
            val eventsList = repository.getEventsByDate(date)
            _events.value = eventsList
        }
    }
}