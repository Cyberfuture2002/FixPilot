package com.example.fixpilot.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TextWithInfoTooltip(
    textWithMarkers: String,
    tooltipData: Map<String, String>,
    modifier: Modifier = Modifier
) {
    // Regex sucht Begriffe in geschweiften Klammern, z.B. {Grafikkarte}
    val regex = Regex("\\{(\\w+)\\}")

    val matches = regex.findAll(textWithMarkers).toList()
    var lastIndex = 0

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        for ((i, match) in matches.withIndex()) {
            val start = match.range.first
            val end = match.range.last + 1

            // Text vor Marker ausgeben
            if (start > lastIndex) {
                Text(textWithMarkers.substring(lastIndex, start))
            }

            val key = match.groupValues[1]
            val explanation = tooltipData[key] ?: "Keine Erklärung verfügbar"

            // Hier das Icon mit Tooltip anzeigen
            InfoTooltipIcon(explanation = explanation)

            lastIndex = end

            // Nach letztem Marker Resttext ausgeben
            if (i == matches.lastIndex && end < textWithMarkers.length) {
                Text(textWithMarkers.substring(end))
            }
        }

        // Wenn keine Marker, normalen Text anzeigen
        if (matches.isEmpty()) {
            Text(textWithMarkers)
        }
    }
}

@Composable
fun InfoTooltipIcon(
    explanation: String,
    modifier: Modifier = Modifier
) {
    var showTooltip by remember { mutableStateOf(false) }

    Box(modifier = modifier.padding(horizontal = 4.dp)) {
        IconButton(onClick = { showTooltip = !showTooltip }) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info"
            )
        }

        DropdownMenu(
            expanded = showTooltip,
            onDismissRequest = { showTooltip = false }
        ) {
            Text(
                text = explanation,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
