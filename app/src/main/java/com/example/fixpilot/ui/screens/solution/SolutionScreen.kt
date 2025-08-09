package com.example.fixpilot.ui.screens.solution

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fixpilot.R
import com.example.fixpilot.data.respository.QuestionRepository
import com.example.fixpilot.ui.componets.TextWithInfoTooltip

@Composable
fun SolutionScreen(
    solutionId: String,
    navController: NavHostController
) {
    val solution = remember { QuestionRepository.getQuestion(solutionId) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.solution_title),
            style = MaterialTheme.typography.headlineSmall
        )

        if (solution != null) {
            TextWithInfoTooltip(
                textWithMarkers = solution.text,
                tooltipData = QuestionRepository.tooltipInfos
            )
        } else {
            Text(text = stringResource(R.string.no_solution_found))
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                navController.navigate("questionFlow") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.start_new_question_tree))
        }
    }
}
