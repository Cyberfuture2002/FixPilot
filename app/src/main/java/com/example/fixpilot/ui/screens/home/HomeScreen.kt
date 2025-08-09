package com.example.fixpilot.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fixpilot.R
import com.example.fixpilot.viewmodel.AppViewModel

@Composable
fun HomeScreen(navController: NavHostController, appViewModel: AppViewModel) {
    val techFacts = listOf(
        "Der erste Computer war größer als ein Zimmer und hatte weniger Rechenleistung als dein Smartphone.",
        "Weltweit gibt es über 1,3 Milliarden Websites.",
        "Das Internet wurde ursprünglich für die Kommunikation zwischen Forschungseinrichtungen entwickelt.",
        "Das Passwort „123456“ ist immer noch eines der am häufigsten genutzten, obwohl es extrem unsicher ist.",
        "SSD-Festplatten sind deutlich schneller als herkömmliche HDDs, weil sie keine beweglichen Teile haben."
    )

    val currentFactIndex by appViewModel.currentFactIndex.collectAsState()
    val showTechFact by appViewModel.showTechFact.collectAsState()

    var showCard by remember { mutableStateOf(showTechFact) }

    LaunchedEffect(Unit) {
        appViewModel.loadDailyFactIndex(techFacts.size)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (showCard && showTechFact) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Column {
                        Text(
                            text = "Wusstest du?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = techFacts[currentFactIndex],
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    IconButton(
                        onClick = { showCard = false },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Schließen"
                        )
                    }
                }
            }
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

        Button(
            onClick = { navController.navigate("feedback") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(stringResource(id = R.string.feedback_button))
        }

        Spacer(modifier = Modifier.height(16.dp))

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
