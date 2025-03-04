package fr.isen.fougera.isensmartcompanion.data

import androidx.room.*

@Dao
interface InteractionDao {
    @Insert
    suspend fun insertInteraction(interaction: Interaction)

    @Query("SELECT * FROM interactions ORDER BY date DESC")
    fun getAllInteractions(): List<Interaction>

    @Delete
    suspend fun deleteInteraction(interaction: Interaction)

    @Query("DELETE FROM interactions")
    fun deleteAllInteractions()

    @Query("SELECT * FROM interactions WHERE question = :question ORDER BY date DESC LIMIT 1")
    suspend fun getLastInteraction(question: String): Interaction?
}