package fr.isen.fougera.isensmartcompanion.API

import retrofit2.Call
import retrofit2.http.GET

import fr.isen.fougera.isensmartcompanion.Event.Event

interface ApiService {
    @GET("events.json")
    fun getEvents(): Call<List<Event>>
}