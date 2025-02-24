package fr.isen.fougera.isensmartcompanion

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GeminiAI {
    private const val API_KEY = "AIzaSyBguWA9SSbLDlRrO6e5RZo3WoZkPpEl7as" // 🔑 Remplace par ta vraie clé API

    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = API_KEY
    )

    suspend fun analyzeText(input: String): String {
        return withContext(Dispatchers.IO) {
            try {
                // ✅ Vérification de l'entrée utilisateur
                if (input.isBlank()) {
                    return@withContext "Veuillez poser une question valide."
                }

                // ✅ Envoi direct du texte à Gemini
                Log.d("GEMINI_REQUEST", "Question envoyée : $input")
                val response = model.generateContent(input) // ✅ Envoie le texte en `String`

                // ✅ Vérification et récupération de la réponse
                val result = response.text?.trim() ?: "Je n'ai pas compris votre question."
                Log.d("GEMINI_RESPONSE", "Réponse de l'IA : $result")
                result
            } catch (e: Exception) {
                Log.e("GEMINI_ERROR", "Erreur lors de l'appel à l'IA : ${e.message}")
                "Erreur lors de l'appel à l'IA : ${e.message}"
            }
        }
    }
}