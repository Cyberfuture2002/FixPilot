package com.example.fixpilot.ui.screens.questionflow

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fixpilot.R
import com.example.fixpilot.data.PreferenceHelper
import com.example.fixpilot.data.model.Question
import com.example.fixpilot.data.respository.QuestionRepository
import com.example.fixpilot.ui.componets.TextWithInfoTooltip
import com.example.fixpilot.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
                stringResource(R.string.question_flow_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            if (!started) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = showExplanation,
                                onCheckedChange = {
                                    showExplanation = it
                                    prefs.saveShowExplanation(it)
                                }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(stringResource(R.string.question_flow_show_explanation))
                        }

                        if (showExplanation) {
                            Text(
                                stringResource(R.string.question_flow_explanation_text),
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
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
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

                            if (nextQ != null) newList.add(nextQ to null)

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
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.question_flow_solution_button))
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextWithInfoTooltip(
                textWithMarkers = question.text,
                tooltipData = QuestionRepository.tooltipInfos,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (question.answers.isEmpty()) {
                Text(stringResource(R.string.question_flow_last_question))
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
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
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
