package fr.isen.fougera.isensmartcompanion

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AssistantScreen() {
    var question by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // Pour aligner le contenu en haut et la barre en bas
    ) {
        // 📌 Titre ISEN Smart Companion
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "ISEN",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB71C1C) // 🔴 Rouge ISEN
            )
            Text(
                text = "Smart Companion",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }

        // 🔲 ESPACE VIDE AU MILIEU (pour bien centrer les éléments)
        Spacer(modifier = Modifier.weight(1f))

        // 📩 Champ de texte + bouton envoyer en bas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ✏️ Champ de saisie
            TextField(
                value = question,
                onValueChange = { question = it },
                placeholder = { Text("Posez votre question...") },
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            // 📩 Bouton envoyer
            Button(
                onClick = {
                    Toast.makeText(context, "Question envoyée", Toast.LENGTH_SHORT).show()
                    question = "" // 🔄 Efface la question après envoi
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape), // 🔘 Rond comme sur ton design
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C)) // 🔴 Rouge ISEN
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Envoyer",
                    tint = Color.White
                )
            }
        }
    }
}