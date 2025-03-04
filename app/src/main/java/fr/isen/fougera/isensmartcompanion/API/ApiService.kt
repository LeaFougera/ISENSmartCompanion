package fr.isen.fougera.isensmartcompanion.API

import retrofit2.Call
import retrofit2.http.GET

// ✅ Ajoute cet import pour que `Event` soit reconnu
import fr.isen.fougera.isensmartcompanion.Event.Event

interface ApiService {
    @GET("events.json") // 🔗 Endpoint de l'API
    fun getEvents(): Call<List<Event>> // ✅ Correction pour reconnaître Event
}