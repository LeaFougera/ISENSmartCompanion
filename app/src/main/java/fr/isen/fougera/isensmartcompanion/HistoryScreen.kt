package fr.isen.fougera.isensmartcompanion

import android.widget.Toast
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.isen.fougera.isensmartcompanion.data.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext // Ajout de l'import de LocalContext

@Composable
fun HistoryScreen(viewModel: InteractionViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val interactionHistory by viewModel.allInteractions.collectAsState(initial = emptyList())
    val context = LocalContext.current // Utilisation de LocalContext dans un composable

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "📜 Historique des questions", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // 📜 Liste des interactions
        LazyColumn {
            items(interactionHistory) { interaction ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📅 ${formatDate(interaction.date)}", fontSize = 14.sp, color = Color.Gray)
                        Text("❓ ${interaction.question}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("🤖 ${interaction.answer}", fontSize = 16.sp)

                        // 🗑️ Bouton de suppression individuelle
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    viewModel.deleteInteraction(interaction)
                                    // Afficher Toast après suppression
                                    Toast.makeText(context, "Interaction supprimée", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Supprimer", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔴 Bouton pour supprimer tout l'historique
        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.deleteAllInteractions() // Supprime tout l'historique
                    // Afficher Toast après suppression de tout l'historique
                    Toast.makeText(context, "Historique supprimé", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Effacer tout l'historique", color = Color.White)
        }
    }
}

// ✅ Fonction pour convertir le timestamp en date lisible
fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}