package com.example.fixpilot.ui.screens.solution

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fixpilot.data.respository.QuestionRepository

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
            text = "Deine Lösung",
            style = MaterialTheme.typography.headlineSmall
        )
        if (solution != null) {
            Text(text = solution.text)
        } else {
            Text(text = "Keine Lösung gefunden.")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                navController.navigate("questionFlow") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Neuen Fragebaum starten")
        }
    }
}
