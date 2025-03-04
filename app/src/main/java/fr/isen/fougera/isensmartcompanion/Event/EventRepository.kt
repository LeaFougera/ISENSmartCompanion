package fr.isen.fougera.isensmartcompanion.Event

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventRepository(context: Context) {
    private val eventDao: EventDao = EventDatabase.getDatabase(context).eventDao()

    suspend fun insertEvent(event: Event) {
        withContext(Dispatchers.IO) {
            eventDao.insertEvent(event)
        }
    }

    suspend fun getEventsByDate(date: String): List<Event> {
        return withContext(Dispatchers.IO) {
            eventDao.getEventsByDate(date)
        }
    }
}