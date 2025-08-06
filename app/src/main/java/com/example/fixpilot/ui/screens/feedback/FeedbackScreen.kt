package com.example.fixpilot.ui.screens.feedback

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(navController: NavHostController) {
    var category by remember { mutableStateOf("allgemein") }
    var feedbackText by remember { mutableStateOf("") }
    var errorCode by remember { mutableStateOf("") }
    var errorContext by remember { mutableStateOf("betriebssystem") }
    var appName by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    var showThanksSnackbar by remember { mutableStateOf(false) }

    if (showThanksSnackbar) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar("Danke für dein Feedback!")
            showThanksSnackbar = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Feedback geben") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                "Wir freuen uns über dein Feedback!",
                style = MaterialTheme.typography.headlineSmall
            )

            // Kategorie
            Column {
                Text("Kategorie auswählen:", style = MaterialTheme.typography.titleMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = category == "allgemein",
                        onClick = { category = "allgemein" }
                    )
                    Text("Allgemein")
                    Spacer(Modifier.width(16.dp))
                    RadioButton(
                        selected = category == "fehlercode",
                        onClick = { category = "fehlercode" }
                    )
                    Text("Fehlercode melden")
                }
            }

            // Fehlercode-Bereich
            if (category == "fehlercode") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = errorCode,
                        onValueChange = { errorCode = it },
                        label = { Text("Fehlercode") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Fehler in:", style = MaterialTheme.typography.titleMedium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = errorContext == "betriebssystem",
                            onClick = { errorContext = "betriebssystem" }
                        )
                        Text("Betriebssystem")
                        Spacer(Modifier.width(16.dp))
                        RadioButton(
                            selected = errorContext == "app",
                            onClick = { errorContext = "app" }
                        )
                        Text("App")
                    }

                    if (errorContext == "app") {
                        OutlinedTextField(
                            value = appName,
                            onValueChange = { appName = it },
                            label = { Text("App-Name eingeben") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Freitext
            OutlinedTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                label = { Text("Dein Feedback") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 6
            )

            // Absenden
            Button(
                onClick = {
                    feedbackText = ""
                    errorCode = ""
                    appName = ""
                    category = "allgemein"
                    errorContext = "betriebssystem"
                    showThanksSnackbar = true
                },
                enabled = feedbackText.isNotBlank() || (category == "fehlercode" && errorCode.isNotBlank()),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Feedback absenden")
            }
        }
    }
}
