package com.example.fixpilot.ui.screens.questionflow

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.fixpilot.R
import com.example.fixpilot.data.PreferenceHelper
import com.example.fixpilot.data.model.Question
import com.example.fixpilot.data.respository.QuestionRepository
import com.example.fixpilot.viewmodel.AppViewModel

@Composable
fun FragebaumScreen(
    navController: NavHostController,
    viewModel: AppViewModel
) {
    val context = LocalContext.current
    val prefs = remember { PreferenceHelper(context) }

    val alreadyStarted = prefs.loadQuestionFlowStarted()
    var showExplanation by rememberSaveable { mutableStateOf(prefs.loadShowExplanation()) }
    var started by rememberSaveable { mutableStateOf(alreadyStarted) }
    var askedQuestions by remember { mutableStateOf<List<Pair<Question, String?>>>(emptyList()) }

    LaunchedEffect(started) {
        if (started && askedQuestions.isEmpty()) {
            QuestionRepository.getQuestion("q1")?.let {
                askedQuestions = listOf(it to null)
            }
            prefs.saveQuestionFlowStarted(true)
        }
    }

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                (stringResource(R.string.question_flow_title)),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            if (!started) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = showExplanation,
                                onCheckedChange = {
                                    showExplanation = it
                                    prefs.saveShowExplanation(it)
                                }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text((stringResource(R.string.question_flow_show_explanation)), style = MaterialTheme.typography.bodyLarge)
                        }

                        if (showExplanation) {
                            Text(
                                (stringResource(R.string.question_flow_explanation_text)),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp)
                            )
                        }

                        Button(
                            onClick = { started = true },
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = stringResource(R.string.start_icon_description))
                            Spacer(Modifier.width(8.dp))
                            Text(stringResource(R.string.question_flow_start_button))
                        }
                    }
                }
                return@Column
            }

            val lastIndex = askedQuestions.lastIndex
            askedQuestions.forEachIndexed { index, (question, selectedAnswer) ->
                val isLast = index == lastIndex
                val isEndNode = question.answers.isEmpty()

                if (!(isLast && isEndNode)) {
                    FragebaumFrage(
                        question = question,
                        selectedAnswer = selectedAnswer,
                        onAnswerSelected = { newAnswer ->
                            val newList = askedQuestions.subList(0, index + 1).toMutableList()
                            newList[index] = question to newAnswer

                            val nextId = question.answers.find { it.text == newAnswer }?.nextQuestionId
                            val nextQ = nextId?.let { QuestionRepository.getQuestion(it) }

                            if (nextQ != null) {
                                newList.add(nextQ to null)
                            }

                            askedQuestions = newList
                        }
                    )
                }
            }

            val last = askedQuestions.lastOrNull()
            if (last != null && last.first.answers.isEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { navController.navigate("solution/${last.first.id}") },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = stringResource(R.string.solution_icon_description), tint = MaterialTheme.colorScheme.onPrimary)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.question_flow_solution_button), color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FragebaumFrage(
    question: Question,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit
) {
    // Tooltip-Begriffe mit Erklärungen, hier einfach Beispiele:
    val tooltipInfos = mapOf(
        "Grafikkarte" to "Eine Grafikkarte verarbeitet Bilddaten und gibt sie an den Monitor weiter.",
        "CPU_Temperatur" to "Die Temperatur der CPU sollte nicht zu hoch werden, sonst kann der PC abstürzen."
        // Weitere Begriffe nach Bedarf
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextWithInfoTooltip(
                textWithMarkers = question.text,
                tooltipData = tooltipInfos,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (question.answers.isEmpty()) {
                Text(
                    stringResource(R.string.question_flow_last_question),
                    style = MaterialTheme.typography.bodyMedium,
                )
                return@Column
            }

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedAnswer ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.question_flow_answer_label)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    question.answers.forEach { answer ->
                        DropdownMenuItem(
                            text = { Text(answer.text) },
                            onClick = {
                                onAnswerSelected(answer.text)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextWithInfoTooltip(
    textWithMarkers: String,
    tooltipData: Map<String, String>,
    modifier: Modifier = Modifier
) {
    // Zerlege Text an Markern wie {Begriff}
    val regex = Regex("""\{(\w+)\}""")
    val matches = regex.findAll(textWithMarkers).toList()
    var lastIndex = 0

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        for ((i, match) in matches.withIndex()) {
            val start = match.range.first
            val end = match.range.last + 1

            // Text vor Marker
            if (start > lastIndex) {
                Text(textWithMarkers.substring(lastIndex, start))
            }

            val key = match.groupValues[1]
            val explanation = tooltipData[key] ?: "Keine Erklärung verfügbar"

            InfoTooltipIcon(text = key, explanation = explanation)

            lastIndex = end

            // Nach letztem Marker Resttext
            if (i == matches.lastIndex && end < textWithMarkers.length) {
                Text(textWithMarkers.substring(end))
            }
        }

        if (matches.isEmpty()) {
            Text(textWithMarkers)
        }
    }
}

@Composable
fun InfoTooltipIcon(text: String, explanation: String) {
    var showTooltip by remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(horizontal = 4.dp)) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { showTooltip = !showTooltip }
        )
        if (showTooltip) {
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(0.6f),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(
                    text = explanation,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
