package fr.isen.fougera.isensmartcompanion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Simule un historique (à remplacer par une base de données Room plus tard)
val fakeHistory = listOf(
    "Q: Comment gérer mon emploi du temps ?\nA: Utilise un planning hebdomadaire.",
    "Q: Y a-t-il un tutorat à l'ISEN ?\nA: Oui, contacte le BDE pour plus d'informations.",
    "Q: Quels événements sont prévus ce mois-ci ?\nA: Soirée BDE, Gala ISEN et Journée de Cohésion."
)

@Composable
fun HistoryScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "📜 Historique des questions", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(fakeHistory) { historyItem ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = historyItem, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}