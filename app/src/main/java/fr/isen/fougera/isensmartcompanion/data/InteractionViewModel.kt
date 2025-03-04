package fr.isen.fougera.isensmartcompanion.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InteractionViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val interactionDao = db.interactionDao()

    private val _allInteractions = MutableStateFlow<List<Interaction>>(emptyList())
    val allInteractions = _allInteractions.asStateFlow()


    init {
        // Chargement des interactions à partir de la base de données
        viewModelScope.launch(Dispatchers.IO) {
            _allInteractions.value = interactionDao.getAllInteractions()
        }
    }


    // Insérer une interaction et mettre à jour l'historique
    fun insertInteraction(question: String, answer: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val interaction = Interaction(
                question = question,
                answer = answer,
                date = System.currentTimeMillis()
            )
            interactionDao.insertInteraction(interaction)
        }
    }

    fun deleteInteraction(interaction: Interaction) {
        viewModelScope.launch {
            interactionDao.deleteInteraction(interaction)

            _allInteractions.value = interactionDao.getAllInteractions()
        }
    }

    fun deleteAllInteractions() {
        viewModelScope.launch(Dispatchers.IO) {
            interactionDao.deleteAllInteractions()

            _allInteractions.value = interactionDao.getAllInteractions()
        }
    }
}