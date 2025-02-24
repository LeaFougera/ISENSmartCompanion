package fr.isen.fougera.isensmartcompanion

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("events.json") // 🔗 URL de l'endpoint
    fun getEvents(): Call<List<Event>>
}