package com.example.fixpilot.ui.screens.feedback

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fixpilot.R

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

    val thanksMessage = stringResource(R.string.feedback_thanks)

    if (showThanksSnackbar) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(thanksMessage)
            showThanksSnackbar = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.feedback_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back_button))
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
                stringResource(R.string.feedback_welcome),
                style = MaterialTheme.typography.headlineSmall
            )

            // Kategorie
            Column {
                Text(stringResource(R.string.feedback_category_label), style = MaterialTheme.typography.titleMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = category == "allgemein",
                        onClick = { category = "allgemein" }
                    )
                    Text(stringResource(R.string.feedback_category_general))
                    Spacer(Modifier.width(16.dp))
                    RadioButton(
                        selected = category == "fehlercode",
                        onClick = { category = "fehlercode" }
                    )
                    Text(stringResource(R.string.feedback_category_error_code))
                }
            }

            // Fehlercode-Bereich
            if (category == "fehlercode") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = errorCode,
                        onValueChange = { errorCode = it },
                        label = { Text(stringResource(R.string.feedback_error_code_label)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(stringResource(R.string.feedback_error_context_label), style = MaterialTheme.typography.titleMedium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = errorContext == "betriebssystem",
                            onClick = { errorContext = "betriebssystem" }
                        )
                        Text(stringResource(R.string.feedback_error_context_os))
                        Spacer(Modifier.width(16.dp))
                        RadioButton(
                            selected = errorContext == "app",
                            onClick = { errorContext = "app" }
                        )
                        Text(stringResource(R.string.feedback_error_context_app))
                    }

                    if (errorContext == "app") {
                        OutlinedTextField(
                            value = appName,
                            onValueChange = { appName = it },
                            label = { Text(stringResource(R.string.feedback_app_name_label)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Freitext
            OutlinedTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                label = { Text(stringResource(R.string.feedback_text_label)) },
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
                Text(stringResource(R.string.feedback_send_button))
            }
        }
    }
}
