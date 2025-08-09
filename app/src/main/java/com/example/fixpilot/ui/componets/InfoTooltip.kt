package com.example.fixpilot.ui.componets

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable


/**
 * Nutzt {Begriff} im Text, um an diesen Stellen einen Tooltip anzuzeigen.
 */
@Composable
fun TextWithInfoTooltip(
    textWithMarkers: String,
    tooltipData: Map<String, String>,
    modifier: Modifier = Modifier
) {
    val regex = Regex("\\{([^}]+)\\}")
    val matches = regex.findAll(textWithMarkers).toList()
    var lastIndex = 0

    Row(
        modifier = modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (matches.isEmpty()) {
            Text(textWithMarkers)
            return
        }

        matches.forEachIndexed { index, match ->
            val start = match.range.first
            val end = match.range.last + 1

            // normalen Text vor Marker
            if (start > lastIndex) {
                Text(textWithMarkers.substring(lastIndex, start))
            }

            val key = match.groupValues[1]
            val explanation = tooltipData[key] ?: "Keine Erklärung verfügbar"

            InfoTooltipLabel(label = key, explanation = explanation)

            lastIndex = end

            // Text nach letztem Marker
            if (index == matches.lastIndex && end < textWithMarkers.length) {
                Text(textWithMarkers.substring(end))
            }
        }
    }
}

@Composable
fun InfoTooltipLabel(label: String, explanation: String) {
    var showTooltip by remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(horizontal = 4.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { showTooltip = !showTooltip }
                .padding(2.dp)
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        DropdownMenu(
            expanded = showTooltip,
            onDismissRequest = { showTooltip = false },
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = explanation,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
