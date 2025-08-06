package com.example.fixpilot.ui.screens.einstellungen

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fixpilot.R
import com.example.fixpilot.util.LanguageManager
import com.example.fixpilot.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EinstellungenScreen(navController: NavHostController, viewModel: AppViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity

    val darkMode by viewModel.darkModeEnabled.collectAsState()

    val languageGerman = stringResource(R.string.language_german)
    val languageEnglish = stringResource(R.string.language_english)
    val languages = listOf(languageGerman, languageEnglish)

    val currentLanguage = remember { mutableStateOf(LanguageManager.getCurrentLanguage(context)) }
    var expanded by remember { mutableStateOf(false) }
    var languageChanged by remember { mutableStateOf(false) }

    LaunchedEffect(languageChanged) {
        if (languageChanged) {
            activity?.recreate()
            languageChanged = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.settings))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(stringResource(R.string.dark_mode), style = MaterialTheme.typography.titleMedium)

            // Dark Mode Umschalter
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.dark_mode))
                Switch(
                    checked = darkMode,
                    onCheckedChange = { viewModel.setDarkModeEnabled(it) }
                )
            }

            Divider()

            Text(stringResource(R.string.language), style = MaterialTheme.typography.titleMedium)

            // Sprache auswählen
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Language, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        when (currentLanguage.value) {
                            "de" -> languageGerman
                            "en" -> languageEnglish
                            else -> stringResource(R.string.language_system)
                        }
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    languages.forEach { language ->
                        DropdownMenuItem(
                            text = { Text(language) },
                            onClick = {
                                expanded = false
                                val langCode = when (language) {
                                    languageGerman -> "de"
                                    languageEnglish -> "en"
                                    else -> "de"
                                }
                                LanguageManager.setLanguage(context, langCode)
                                currentLanguage.value = langCode
                                languageChanged = true
                            }
                        )
                    }
                }
            }

            Divider()

            Text(stringResource(R.string.privacy_imprint), style = MaterialTheme.typography.titleMedium)

            OutlinedButton(
                onClick = { /* TODO: Datenschutz anzeigen */ },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.PrivacyTip, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.privacy_imprint))
            }

            OutlinedButton(
                onClick = { navController.navigate("feedback") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Feedback, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.feedback_button))
            }
        }
    }
}
