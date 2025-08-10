package com.example.fixpilot.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.verticalScroll
import androidx.navigation.NavController
import com.example.fixpilot.data.PreferenceHelper

import com.example.fixpilot.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random



@Composable
fun HomeScreen(navController: NavController, prefs: PreferenceHelper) {
    val scrollState = rememberScrollState()

    val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    val techFacts = listOf(
        "Wusstest du, dass die erste E-Mail 1971 verschickt wurde?",
        "Der erste Computer-Virus wurde 1986 erstellt.",
        "USB steht für Universal Serial Bus.",
        "Das erste Smartphone kam 1994 auf den Markt.",
        "Die erste Webseite ging 1991 online."
        // ... hier deine weiteren Facts
    )

    var showCard by remember { mutableStateOf(false) }
    var currentFact by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val lastDate = prefs.loadLastFactShownDate()
        val showFactSetting = prefs.loadShowTechFact()

        if (showFactSetting) {
            if (lastDate != today) {
                // Neuer Tag → neuen zufälligen Fact auswählen
                val newIndex = Random.nextInt(techFacts.size)
                prefs.saveLastFactIndex(newIndex)
                prefs.saveLastFactShownDate(today)
                currentFact = techFacts[newIndex]
                showCard = true
            } else {
                // Gleicher Tag → alten Fact laden
                val lastIndex = prefs.loadLastFactIndex()
                if (lastIndex != -1) {
                    currentFact = techFacts[lastIndex]
                    showCard = false // nur anzeigen, wenn heute noch nicht geschlossen
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        if (showCard) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = currentFact, style = MaterialTheme.typography.bodyLarge)
                    IconButton(onClick = { showCard = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Schließen")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = stringResource(id = R.string.welcome_message),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = stringResource(id = R.string.welcome_description),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        FeatureCard(
            title = "🔍 " + stringResource(id = R.string.question_flow),
            description = stringResource(id = R.string.question_flow_description),
            onClick = { navController.navigate("questionFlow") }
        )

        FeatureCard(
            title = "📚 " + stringResource(id = R.string.error_database),
            description = stringResource(id = R.string.error_database_description),
            onClick = { navController.navigate("errorDatabase") }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), thickness = 1.dp)

        Text(
            text = stringResource(id = R.string.additional_info_line1),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(id = R.string.additional_info_line2),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(id = R.string.additional_info_line3),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(id = R.string.additional_info_line4),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(id = R.string.additional_info_line5),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), thickness = 1.dp)

        Text(
            text = stringResource(id = R.string.tagline),
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(id = R.string.app_slogan),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 20.dp)
        )

        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), thickness = 1.dp)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "🧪 " + stringResource(id = R.string.beta_version),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = stringResource(id = R.string.beta_info),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = "📰 " + stringResource(id = R.string.news_info),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun FeatureCard(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
