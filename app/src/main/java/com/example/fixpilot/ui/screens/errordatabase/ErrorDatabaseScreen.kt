package com.example.fixpilot.ui.screens.errordatabase

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.fixpilot.data.model.ErrorEntry
import com.example.fixpilot.data.respository.ErrorDatabase
import com.example.fixpilot.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDatabaseScreen(navController: NavHostController) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedSystem by remember { mutableStateOf<String?>(null) }
    var errorCode by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<ErrorEntry?>(null) }
    var hasSearched by remember { mutableStateOf(false) }

    val categories = listOf("Betriebssystem")
    val systems = if (selectedCategory != null) listOf("Windows")
    else emptyList()

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                stringResource(R.string.error_db_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            SimpleCardDropdown(
                label = stringResource(R.string.error_db_category_label),
                options = categories,
                selected = selectedCategory,
                onSelect = {
                    selectedCategory = it
                    selectedSystem = null
                    errorCode = ""
                    result = null
                    hasSearched = false
                }
            )

            if (selectedCategory != null) {
                SimpleCardDropdown(
                    label = stringResource(R.string.error_db_system_label),
                    options = systems,
                    selected = selectedSystem,
                    onSelect = {
                        selectedSystem = it
                        errorCode = ""
                        result = null
                        hasSearched = false
                    }
                )
            }

            if (selectedCategory != null && selectedSystem != null) {
                val suggestions = remember(errorCode) {
                    if (errorCode.isNotBlank()) {
                        ErrorDatabase.getErrorCodesForCategory(selectedCategory!!)
                            .filter { it.startsWith(errorCode, ignoreCase = true) }
                            .take(5)
                    } else emptyList()
                }

                SearchDropdownField(
                    label = stringResource(R.string.error_db_code_input),
                    query = errorCode,
                    onQueryChange = {
                        errorCode = it
                        result = null
                        hasSearched = false
                    },
                    suggestions = suggestions
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        result = ErrorDatabase.findError(
                            category = selectedCategory!!,
                            system = selectedSystem!!,
                            program = null,
                            errorCode = errorCode.trim()
                        )
                        hasSearched = true
                    },
                    enabled = errorCode.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Suchen Icon")
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.search_button))
                }
            }

            Spacer(Modifier.height(24.dp))

            if (hasSearched) {
                if (result != null) {
                    Text(
                        stringResource(R.string.error_db_description_label),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(result!!.description, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        stringResource(R.string.error_db_solution_label),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(result!!.solution, style = MaterialTheme.typography.bodyMedium)
                } else if (errorCode.isNotBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "Warnung",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            stringResource(R.string.error_db_not_found),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleCardDropdown(
    label: String,
    options: List<String>,
    selected: String?,
    onSelect: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onSelect(option) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == option,
                        onClick = { onSelect(option) }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = option, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDropdownField(
    label: String,
    query: String,
    onQueryChange: (String) -> Unit,
    suggestions: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldWidthPx by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    Column {
        OutlinedTextField(
            value = query,
            onValueChange = { new ->
                onQueryChange(new)
                expanded = new.isNotBlank() && suggestions.isNotEmpty()
            },
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coords ->
                    textFieldWidthPx = coords.size.width
                },
            singleLine = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(density) { textFieldWidthPx.toDp() })
        ) {
            suggestions.forEach { suggestion ->
                DropdownMenuItem(
                    text = { Text(suggestion) },
                    onClick = {
                        onQueryChange(suggestion)
                        expanded = false
                    }
                )
            }
        }
    }
}
