package fr.isen.fougera.isensmartcompanion

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.fougera.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ISENSmartCompanionTheme {
                AssistantScreen()
            }
        }
    }
}

@Composable
fun AssistantScreen() {
    var question by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("Posez-moi une question...") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // Aligne les éléments en haut et en bas
    ) {
        // TITRE ISEN + Smart Companion
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "ISEN",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB71C1C) // Rouge foncé
            )
            Text(
                text = "Smart Companion",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }

        // ESPACE VIDE AU MILIEU
        Spacer(modifier = Modifier.weight(1f))

        // AFFICHAGE DE LA RÉPONSE
        Text(
            text = response,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // CHAMP DE TEXTE + BOUTON ENVOYER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // CHAMP DE TEXTE
            TextField(
                value = question,
                onValueChange = { question = it },
                placeholder = { Text("Posez votre question...") },
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            // BOUTON ENVOYER
            Button(
                onClick = {
                    response = "Vous avez demandé : $question"
                    Toast.makeText(context, "Question envoyée", Toast.LENGTH_SHORT).show()
                    question = "" // Efface la question après l'envoi
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C)) // Rouge foncé
            ) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_send), // Icône système d'envoi
                    contentDescription = "Envoyer",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAssistantScreen() {
    ISENSmartCompanionTheme {
        AssistantScreen()
    }
}