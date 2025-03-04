package fr.isen.fougera.isensmartcompanion.Event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(event: Event)

    @Query("SELECT * FROM events WHERE date = :date")
    fun getEventsByDate(date: String): List<Event>

    @Query("SELECT * FROM events")
    fun getAllEvents(): List<Event>
}